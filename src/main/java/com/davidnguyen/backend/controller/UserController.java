package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.*;
import com.davidnguyen.backend.model.User;
import com.davidnguyen.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> findUsersWithPagination(
            @Valid @RequestBody FindUserDTO findUserDTO) {

        // Lấy danh sách người dùng với phân trang
        List<User> users = userService.findUsersWithPagination(findUserDTO.getOffset(), findUserDTO.getLimit());

        // Đếm tổng số người dùng
        Integer total = userService.countAllUsers();

        // Tạo response map
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("total", total);

        // Trả về ResponseEntity chứa map
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/user")
    public ResponseEntity<Map<String, Integer>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(userService.createAnUser(createUserDTO));
    }

    @DeleteMapping("/delete/users")
    public ResponseEntity<Map<String, Integer>> deleteUser(@Valid @RequestBody DeleteUserDTO deleteUserDTO) {
        return ResponseEntity.ok(userService.deleteUsersById(deleteUserDTO));
    }

    @PutMapping("/update/users")
    public ResponseEntity<Integer> updateUsersById(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUsersById(updateUserDTO));
    }

    @PostMapping("/demo")
    public ResponseEntity<Map<String, Object>> demo(@Valid @RequestBody DemoDTO demoDTO) {
        Map<String, Object> response = new HashMap<>();
        log.info("demo demo");
        response.put("demo", demoDTO.getUserIds());
        return ResponseEntity.ok(response);
    }

}
