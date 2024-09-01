package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserDTO {
    @NotNull(message = "limit is required")
    @Min(value = 0, message = "Min is 0")
    @Max(value = 20, message = "Max is 20")
    private Integer limit;

    @NotNull(message = "offset is required")
    @Min(value = 0, message = "Min is 0")
    @Max(value = 20, message = "Max is 20")
    private Integer offset;
}
