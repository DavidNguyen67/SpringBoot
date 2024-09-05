package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.ValidStringList;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteRolesDTO {
    @NotNull
    @Size(min = 1)
    @ValidStringList
    private List<String> roleIds;
}
