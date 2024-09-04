package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateUserDTO;
import com.davidnguyen.backend.dto.DeleteUserDTO;
import com.davidnguyen.backend.dto.UpdateUserDTO;
import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.repository.UserRepository;
import com.davidnguyen.backend.repository.UserRoleRepository;
import com.davidnguyen.backend.utility.constant.UserConstant;
import com.davidnguyen.backend.utility.helper.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private I18nService i18nService;

    public List<User> findUsersWithPagination(int offset, int limit) {
        List<User> users = userRepository.findUsersWithPagination(offset, limit);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18nService.getMessage("messages.noUsersFound"));
        }
        return users;
    }

    public Integer countAllUsers() {
        return userRepository.countUsers();
    }

    /**
     * Annotation này cho biết phương thức được đánh dấu sẽ được thực thi trong bối cảnh của một giao dịch (transaction).
     * Điều này đảm bảo rằng tất cả các thao tác với cơ sở dữ liệu bên trong phương thức sẽ là một phần của cùng một giao dịch.
     * <p>
     * Thuộc tính `rollbackFor` chỉ định các ngoại lệ nào sẽ kích hoạt việc hoàn tác (rollback) của giao dịch.
     * <p>
     * Trong trường hợp này, giao dịch sẽ bị hoàn tác nếu một ngoại lệ thuộc kiểu `Exception` hoặc `RuntimeException` xảy ra.
     * <p>
     * - `Exception.class`: Hoàn tác giao dịch nếu có bất kỳ ngoại lệ đã kiểm tra (checked exception) nào được ném ra.
     * - `RuntimeException.class`: Hoàn tác giao dịch nếu có bất kỳ ngoại lệ chưa kiểm tra (unchecked exception) nào xảy ra (tức là các ngoại lệ thuộc lớp con của `RuntimeException`).
     * <p>
     * Điều này đảm bảo tính toàn vẹn của dữ liệu bằng cách hoàn tác bất kỳ thay đổi nào được thực hiện bởi phương thức nếu có ngoại lệ xảy ra.
     * <p>
     * Ví dụ sử dụng:
     * {@code
     *
     * @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
     * public void someMethod() {
     * // Triển khai phương thức
     * }
     * }
     * @see org.springframework.transaction.annotation.Transactional
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> createAnUser(CreateUserDTO createUserDTO) {
        Boolean activeValue = createUserDTO.getActive() != null ? createUserDTO.getActive() : UserConstant.ACTIVE;

        // Kiểm tra nếu email đã tồn tại
        List<User> users = userRepository.findUsersByEmail(createUserDTO.getEmail(),
                                                           UserConstant.FIND_EMAIL_CONFLICT_LIMIT);

        if (!users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, i18nService.getMessage("messages.userExists"));
        }

        Integer resultCreateUser = userRepository.createAnUser(createUserDTO.getId(), createUserDTO.getEmail(),
                                                               createUserDTO.getFirstName(),
                                                               createUserDTO.getLastName(),
                                                               activeValue);

        Integer resultCreateUserRoles = userRoleRepository.assignRoleToUser(createUserDTO.getRoleId(),
                                                                            createUserDTO.getUserId(),
                                                                            createUserDTO.getRoleId());

        if (resultCreateUserRoles == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18nService.getMessage("messages.createMappingUserRolesFailed"));
        }

        if (resultCreateUser == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18nService.getMessage("messages.userCreateFailed"));
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("resultCreateUser", resultCreateUser);
        resultMap.put("resultCreateUserRoles", resultCreateUserRoles);
        return resultMap;
    }

    public Integer deleteUsersById(DeleteUserDTO deleteUserDTO) {
        List<String> userIds = deleteUserDTO.getUserIds();

        // Kiểm tra xem các User với các userIds này có tồn tại hay không
        List<User> users = userRepository.findUsersById(userIds);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18nService.getMessage("messages.noUsersFound"));
        }

        Integer result = userRepository.deleteUsersById(userIds);

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18nService.getMessage("messages.userDeleteFailed"));
        }

        return result;
    }

    public Integer updateUsersById(UpdateUserDTO updateUserDTO) {
        List<String> userIds = updateUserDTO.getUserIds();

        if (userIds.size() > 1 && updateUserDTO.getEmail() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              i18nService.getMessage("messages.emailCannotUpdateForMultipleUsers"));
        }

        List<User> users = userRepository.findUsersById(userIds);

        // Kiểm tra xem các User với các userIds này có tồn tại hay không
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18nService.getMessage("messages.noUsersFound"));
        }

        // Duyệt qua các user và cập nhật từng thuộc tính chỉ khi giá trị mới không phải là null
        for (User user : users) {
            if (updateUserDTO.getEmail() != null) {
                user.setEmail(updateUserDTO.getEmail());
            }
            if (updateUserDTO.getFirstName() != null) {
                user.setFirstName(updateUserDTO.getFirstName());
            }
            if (updateUserDTO.getLastName() != null) {
                user.setLastName(updateUserDTO.getLastName());
            }
            if (updateUserDTO.getActive() != null) {
                user.setActive(updateUserDTO.getActive());
            }
        }

        List<User> savedUsers = userRepository.saveAll(
                users);

        if (savedUsers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18nService.getMessage("messages.userUpdateFailed"));
        }

        return savedUsers.size();
    }
}
