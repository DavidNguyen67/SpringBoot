package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteProductDTO {
    @Size
    @AllStringAndNotBlank
    private List<String> productIds;
}
