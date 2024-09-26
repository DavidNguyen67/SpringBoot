package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateRoleDTO;
import com.davidnguyen.backend.dto.DeleteRolesDTO;
import com.davidnguyen.backend.model.Role;
import com.davidnguyen.backend.model.UserRole;
import com.davidnguyen.backend.repository.RolesRepository;
import com.davidnguyen.backend.repository.UserRoleRepository;
import com.davidnguyen.backend.utility.helper.I18nHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;
    private final I18nHelper i18NHelper;
    private final UserRoleRepository userRoleRepository;

    public RolesService(RolesRepository rolesRepository, I18nHelper i18NHelper, UserRoleRepository userRoleRepository) {
        this.rolesRepository = rolesRepository;
        this.i18NHelper = i18NHelper;
        this.userRoleRepository = userRoleRepository;
    }

    public List<Role> findRolesWithPagination(Integer offset, Integer limit) {
        List<Role> roles = rolesRepository.findRolesWithPagination(offset, limit);
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noRolesFound"));
        }
        return roles;
    }

    public List<Role> findRolesById(List<String> roleIds) {
        List<Role> roles = rolesRepository.findRolesById(roleIds);
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18NHelper.getMessage("messages.noRolesFound"));
        }
        return roles;
    }

    public Integer countAllRoles() {
        return rolesRepository.countRoles();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createRole(CreateRoleDTO createRoleDTO) {
        Integer result = rolesRepository.createRole(createRoleDTO.getRoleId(), createRoleDTO.getRoleName());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18NHelper.getMessage("messages.roleCreateFailed"));
        }
        return result;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> deleteRoles(DeleteRolesDTO deleteRolesDTO) {
        List<String> roleIds = deleteRolesDTO.getRoleIds();

        List<Role> roles = rolesRepository.findRolesById(roleIds);
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Roles in rolesRepository found");
        }

        List<UserRole> userRoles = userRoleRepository.findUserRolesByRoleIds(roleIds);
        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No UserRoles in userRoleRepository found");
        }

        Integer resultDeleteRoles = rolesRepository.deleteRolesById(roleIds);
        if (resultDeleteRoles == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18NHelper.getMessage("messages.userDeleteFailed"));
        }

        Integer resultDeleteUserRoles = userRoleRepository.deleteUserRolesByRoleIds(roleIds);
        if (resultDeleteUserRoles == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18NHelper.getMessage("messages.userDeleteFailed"));
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("resultDeleteRoles", resultDeleteRoles);
        result.put("resultDeleteUserRoles", resultDeleteUserRoles);

        return result;
    }

}
