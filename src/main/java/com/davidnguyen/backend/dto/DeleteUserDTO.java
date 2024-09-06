package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringElement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteUserDTO {
    @NotNull(message = "{messages.userIdRequired}")
    @Size(min = 1, message = "{messages.atLeastOneUserIdRequired}")
    @AllStringElement
    private List<String> userIds;

    @NotNull
    @Size(min = 1)
    @AllStringElement
    private List<String> roleIds;
}
