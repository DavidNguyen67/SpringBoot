package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DemoDTO {
    @NotBlank(message = "userIds is required")
    @Email
    private String userIds;
}
