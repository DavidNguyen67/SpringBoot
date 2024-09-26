package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.FindUserRoleDTO;
import com.davidnguyen.backend.model.UserRole;
import com.davidnguyen.backend.repository.UserRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> findUserRolesByUserIds(List<String> userIds) {
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUserIds(userIds);

        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole Not Found");
        }

        return userRoles;
    }

    public List<UserRole> findUserRolesByRoleIds(List<String> roleIds) {
        List<UserRole> userRoles = userRoleRepository.findUserRolesByRoleIds(roleIds);

        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole Not Found");
        }

        return userRoles;
    }

    public List<UserRole> findUniqueUserRolesByRoleIdsOrUserIds(FindUserRoleDTO findUserRoleDTO) {
        List<UserRole> userRoles = userRoleRepository.findUniqueUserRolesByRoleIdsOrUserIds(
                findUserRoleDTO.getUserIds(),
                findUserRoleDTO.getRoleIds());

        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole Not Found");
        }

        return userRoles;
    }

}
