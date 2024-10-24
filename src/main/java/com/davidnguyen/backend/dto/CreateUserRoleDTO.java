package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUserRoleDTO {
    @NotNull
    @Size
    @AllStringAndNotBlank
    private List<String> roleId;

    @NotBlank(message = "{messages.userIdRequired}")
    private String userId;
}
