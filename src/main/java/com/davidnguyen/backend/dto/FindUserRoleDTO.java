package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;


@Getter
public class FindUserRoleDTO {
    @NotNull
    @AllStringAndNotBlank
    List<String> userIds;

    @NotNull
    @AllStringAndNotBlank
    List<String> roleIds;
}
