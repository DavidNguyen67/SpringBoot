package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRoleDTO {
    @NotBlank
    private String mappingId;

    @NotBlank(message = "{messages.roleIdRequired}")
    private String roleId;

    @NotBlank(message = "{messages.userIdRequired}")
    private String userId;
}
