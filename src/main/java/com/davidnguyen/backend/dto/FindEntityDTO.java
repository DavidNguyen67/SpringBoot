package com.davidnguyen.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindEntityDTO {
    @NotNull(message = "{messages.limitRequired}")
    @Min(value = 0, message = "{messages.minIsZero}")
    @Max(value = 20, message = "{messages.maxIsTwenty}")
    private Integer limit;

    @NotNull(message = "{messages.offsetRequired}")
    @Min(value = 0, message = "{messages.minIsZero}")
    @Max(value = 20, message = "{messages.maxIsTwenty}")
    private Integer offset;
}
