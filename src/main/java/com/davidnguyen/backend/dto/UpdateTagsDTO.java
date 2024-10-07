package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateTagsDTO {
    @NotBlank
    @Size(min = 1)
    @AllStringAndNotBlank
    private List<String> ids;

    @NotBlank
    private String newNameTag;
}
