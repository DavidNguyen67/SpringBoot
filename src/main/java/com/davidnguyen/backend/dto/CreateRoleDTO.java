package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleDTO {
    @NotBlank(message = "{messages.roleIdRequired}")
    private String roleId;

    @NotBlank(message = "{messages.roleNameIdRequired}")
    private String roleName;
}
