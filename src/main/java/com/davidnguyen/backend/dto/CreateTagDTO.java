package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTagDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String name;
}
