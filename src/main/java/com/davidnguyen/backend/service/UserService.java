package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateUserDTO;
import com.davidnguyen.backend.dto.DeleteUserDTO;
import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.repository.UserRepository;
import com.davidnguyen.backend.utility.constant.UserConstant;
import com.davidnguyen.backend.utility.helper.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private I18nService I18nService;


    public List<User> findUsersWithPagination(int offset, int limit) {
        List<User> users = userRepository.findUsersWithPagination(offset, limit);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, I18nService.getMessage("no.users.found"));
        }
        return users;
    }

    public Integer countAllUsers() {
        return userRepository.countUsers();
    }

    public Integer createAnUser(CreateUserDTO createUserDTO) {
        Boolean activeValue = createUserDTO.getActive() != null ? createUserDTO.getActive() : UserConstant.ACTIVE;

        // Kiểm tra nếu email đã tồn tại
        List<User> users = userRepository.findUsersByEmail(createUserDTO.getEmail(),
                                                           UserConstant.FIND_EMAIL_CONFLICT_LIMIT);

        if (!users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, I18nService.getMessage("user.exists"));
        }

        Integer result = userRepository.createAnUser(createUserDTO.getId(), createUserDTO.getEmail(),
                                                     createUserDTO.getFirstName(), createUserDTO.getLastName(),
                                                     activeValue);

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              I18nService.getMessage("user.create.failed"));
        }

        return result;
    }

    public Integer deleteUsers(DeleteUserDTO deleteUserDTO) {
        List<String> userIds = deleteUserDTO.getUserIds();

        // Kiểm tra nếu danh sách userIds rỗng
        if (userIds == null || userIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, I18nService.getMessage("no.user.ids.provided"));
        }

        Integer result = userRepository.deleteUsers(userIds);

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              I18nService.getMessage("user.delete.failed"));
        }

        return result;
    }
}
