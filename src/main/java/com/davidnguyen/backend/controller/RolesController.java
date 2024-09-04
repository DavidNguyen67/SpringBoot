package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.CreateRoleDTO;
import com.davidnguyen.backend.dto.FindUserDTO;
import com.davidnguyen.backend.model.Roles;
import com.davidnguyen.backend.service.RolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @PostMapping("roles")
    public ResponseEntity<Map<String, Object>> findRolesWithPagination(@Valid @RequestBody FindUserDTO findUserDTO) {
        // Lấy danh sách người dùng với phân trang
        List<Roles> roles = rolesService.findRolesWithPagination(findUserDTO.getOffset(), findUserDTO.getLimit());

        Integer total = rolesService.countAllRoles();

        // Tạo response map
        Map<String, Object> response = new HashMap<>();
        response.put("roles", roles);
        response.put("total", total);

        // Trả về ResponseEntity chứa map
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/roles")
    public ResponseEntity<Integer> createRole(@Valid @RequestBody CreateRoleDTO createRoleDTO) {
        return ResponseEntity.ok(rolesService.createRole(createRoleDTO));
    }

}
