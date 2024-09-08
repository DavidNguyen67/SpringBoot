package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
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
    @AllStringAndNotBlank
    private List<String> userIds;

    @Email(message = "{messages.invalidEmailAddress}")
    private String email;

    private String firstName;

    private String lastName;

    private Boolean active;
}
