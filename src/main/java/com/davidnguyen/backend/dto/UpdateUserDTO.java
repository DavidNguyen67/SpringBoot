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
    @NotNull(message = "userIds list cannot be null")
    @Size(min = 1, message = "The list must contain at least one userId")
    @ValidStringList
    private List<String> userIds;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    private Boolean active;
}
