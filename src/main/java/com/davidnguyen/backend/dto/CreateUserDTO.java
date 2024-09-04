package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO extends CreateUserRoleDTO {
    @NotBlank(message = "{messages.userIdRequired}")
    private String id;

    @Email(message = "{messages.invalidEmailAddress}")
    private String email;

    @NotBlank(message = "{messages.firstNameRequired}")
    private String firstName;

    @NotBlank(message = "{messages.lastNameRequired}")
    private String lastName;

    private Boolean active;
}
