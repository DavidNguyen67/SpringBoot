package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.ValidStringList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateUserDTO {
    @NotNull(message = "{messages.userIdRequired}")
    @Size(min = 1, message = "{messages.atLeastOneUserIdRequired}")
    @ValidStringList
    private List<String> userIds;

    @Email(message = "{messages.invalidEmailAddress}")
    private String email;

    @NotNull(message = "{messages.firstNameRequired}")
    private String firstName;

    @NotNull(message = "{messages.lastNameRequired}")
    private String lastName;

    private Boolean active;
}
