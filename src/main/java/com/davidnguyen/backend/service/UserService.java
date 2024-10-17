package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateUserDTO;
import com.davidnguyen.backend.dto.DeleteUserDTO;
import com.davidnguyen.backend.dto.UpdateUserDTO;
import com.davidnguyen.backend.dto.UserDTO;
import com.davidnguyen.backend.model.Role;
import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.model.UserRole;
import com.davidnguyen.backend.repository.UserRepository;
import com.davidnguyen.backend.repository.UserRoleRepository;
import com.davidnguyen.backend.utility.constant.UserConstant;
import com.davidnguyen.backend.utility.helper.I18nHelper;
import com.davidnguyen.backend.utility.helper.ObjectMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final I18nHelper i18NHelper;
    private final RolesService rolesService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(I18nHelper i18NHelper, RolesService rolesService, UserRepository userRepository,
                       UserRoleRepository userRoleRepository) {
        this.i18NHelper = i18NHelper;
        this.rolesService = rolesService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserDTO> findUsersWithPagination(Integer offset, Integer limit) {
        // Lấy danh sách users
        List<User> users = userRepository.findUsersWithPagination(offset, limit);

        // Kiểm tra nếu danh sách users rỗng
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noUsersFound"));
        }

        // Lấy danh sách userIds
        List<String> userIds = users.stream().map(User::getId).toList();

        // Lấy danh sách userRoles theo userIds
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUserIds(userIds);

        // Kiểm tra nếu danh sách userRoles rỗng
        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noUserRolesFound"));
        }

        // Lấy danh sách roleIds từ userRoles
        List<String> roleMappingIds = userRoles.stream().map(UserRole::getRoleId).toList();

        // Lấy danh sách roles từ roleMappingIds
        List<Role> roles = rolesService.findRolesById(roleMappingIds);

        // Kiểm tra nếu danh sách roles rỗng
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noRolesFound"));
        }

        // Chuyển đổi danh sách users thành UserDTO
        List<UserDTO> result = ObjectMapperHelper.mapAll(users, UserDTO.class);

        // Gán danh sách vai trò vào UserDTO
        result.forEach((user) -> {
            List<Role> userRole = getUserRoles(user.getId(), userRoles, roles);

            user.setRoles(userRole);
        });

        return result;
    }

    public Integer countAllUsers() {
        return userRepository.countUsers();
    }

    public List<Role> getUserRoles(String userId, List<UserRole> userRolesMapping, List<Role> roles) {
        // Lọc danh sách userRoleIds dựa vào userId và trả ra List<String> roleIds của user đó
        List<String> roleIdsInUserRole = userRolesMapping.stream()
                .filter((userRoleMapping) -> userRoleMapping.getUserId().equals(userId))
                .map(UserRole::getRoleId).toList();

        // Lọc danh sách roles dựa trên roleIdsInUserRole trong List<Role>
        List<Role> userRole = roles.stream()
                .filter(role -> roleIdsInUserRole.contains(role.getId()))
                .toList();

        // Kiểm tra nếu danh sách role của user rỗng
        if (userRole.isEmpty()) {
            log.warn("User {} has no roles assigned", userId);
        }

        log.info("Check userRole size: {}", userRole.size());

        return userRole;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> createAnUser(CreateUserDTO createUserDTO) {
        // Xác định trạng thái active mặc định
        Boolean activeValue = Optional.ofNullable(createUserDTO.getActive()).orElse(UserConstant.ACTIVE);

        // Kiểm tra email tồn tại
        if (!userRepository.findUsersByEmail(createUserDTO.getEmail(), UserConstant.FIND_EMAIL_CONFLICT_LIMIT)
                .isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, i18NHelper.getMessage("messages.userExists"));
        }

        // Tạo người dùng
        Integer resultCreateUser = userRepository.createAnUser(createUserDTO.getId(), createUserDTO.getEmail(),
                createUserDTO.getFirstName(),
                createUserDTO.getLastName(), activeValue);

        if (resultCreateUser == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    i18NHelper.getMessage("messages.userCreateFailed"));
        }

        List<String> roleIds = createUserDTO.getRoleId();
        if (!roleIds.isEmpty()) {
            List<UserRole> userRoleNewRecord = new ArrayList<>();
            roleIds.forEach(roleId -> {
                UUID uuid = UUID.randomUUID();
                log.info("Generated UUID: {}, User ID: {}, Role ID: {}", uuid, createUserDTO.getId(), roleId);
                userRoleNewRecord.add(new UserRole(uuid.toString(), createUserDTO.getId(), roleId));
            });

            List<UserRole> result = userRoleRepository.saveAll(userRoleNewRecord);

            log.info("Saved {} user roles to the repository", result.size());

            if (result.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        i18NHelper.getMessage("messages.userRoleCreateFailed"));
            }
        }


        // Nếu không có roleId, chỉ trả về kết quả tạo user
        return Map.of("resultCreateUser", resultCreateUser, "resultCreateUserRoles", 0);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> deleteUsersById(DeleteUserDTO deleteUserDTO) {
        List<String> userIds = deleteUserDTO.getUserIds();
        List<String> roleIds = deleteUserDTO.getRoleIds();

        // Kiểm tra xem các User với các userIds này có tồn tại hay không
        List<User> users = userRepository.findUsersById(userIds);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noUsersFound"));
        }

        List<UserRole> userRoles = userRoleRepository.findUserRolesByRoleIds(roleIds);
        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole not found");
        }

        Integer resultDeleteUser = userRepository.deleteUsersById(userIds);
        if (resultDeleteUser == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    i18NHelper.getMessage("messages.userDeleteFailed"));
        }

        Integer resultDeleteUserRole = userRoleRepository.deleteUserRolesByRoleIds(roleIds);
        if (resultDeleteUserRole == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DeleteUserRole failed");
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("resultDeleteUserRole", resultDeleteUserRole);
        result.put("resultDeleteUser", resultDeleteUser);

        return result;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer updateUsersById(UpdateUserDTO updateUserDTO) {
        List<String> userIds = updateUserDTO.getUserIds();

        if (userIds.size() > 1 && updateUserDTO.getEmail() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    i18NHelper.getMessage("messages.emailCannotUpdateForMultipleUsers"));
        }

        List<User> users = userRepository.findUsersById(userIds);

        // Kiểm tra xem các User với các userIds này có tồn tại hay không
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noUsersFound"));
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
                    i18NHelper.getMessage("messages.userUpdateFailed"));
        }

        return savedUsers.size();
    }
}
