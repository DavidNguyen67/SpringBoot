package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringElement;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;


@Getter
public class FindUserRoleDTO {
    @NotNull
    @AllStringElement
    List<String> userIds;

    @NotNull
    @AllStringElement
    List<String> roleIds;
}
