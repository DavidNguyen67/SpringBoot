package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.*;
import com.davidnguyen.backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> findUsersWithPagination(
            @RequestParam @Min(0) Integer offset,
            @RequestParam @Min(1) @Max(50) Integer limit) {

        // Lấy danh sách người dùng với phân trang
        List<UserDTO> users = userService.findUsersWithPagination(offset, limit);

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
