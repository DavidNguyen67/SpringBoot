package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DemoDTO {
    @NotBlank(message = "{messages.userIdRequired}")
    private String userIds;
}
