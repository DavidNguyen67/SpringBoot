package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.ValidStringList;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteUserDTO {
    @NotNull(message = "userIds list cannot be null")
    @Size.List(@Size(min = 1, message = "The list must contain at least one userId"))
    @ValidStringList
    private List<String> userIds;
}
