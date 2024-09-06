package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.FindUserRoleDTO;
import com.davidnguyen.backend.model.UserRole;
import com.davidnguyen.backend.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/userRoles")
    public ResponseEntity<List<UserRole>> findUserRolesByUserIds(@Valid @RequestBody FindUserRoleDTO findUserRoleDTO) {
        return ResponseEntity.ok(userRoleService.findUniqueUserRolesByRoleIdsOrUserIds(findUserRoleDTO));
    }

}
