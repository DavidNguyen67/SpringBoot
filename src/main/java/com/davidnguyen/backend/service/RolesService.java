package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateRoleDTO;
import com.davidnguyen.backend.model.Roles;
import com.davidnguyen.backend.repository.RolesRepository;
import com.davidnguyen.backend.utility.helper.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private I18nService i18nService;

    public List<Roles> findRolesWithPagination(Integer offset, Integer limit) {
        List<Roles> roles = rolesRepository.findRolesWithPagination(offset, limit);
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, i18nService.getMessage("messages.noRolesFound"));
        }
        return roles;
    }

    public Integer countAllRoles() {
        return rolesRepository.countRoles();
    }

    public Integer createRole(CreateRoleDTO createRoleDTO) {
        Integer result = rolesRepository.createRole(createRoleDTO.getRoleId(), createRoleDTO.getRoleName());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              i18nService.getMessage("messages.roleCreateFailed"));
        }
        return result;
    }

}
