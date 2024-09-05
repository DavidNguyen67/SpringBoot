package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoDTO {
    @NotBlank(message = "{messages.userIdRequired}")
    private String userIds;
}
