package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.validation.AllStringAndNotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductDTO {
    @NotBlank
    private String id;

    private String description;

    private Double discountPrice;

    @NotBlank
    private String name;

    @NotBlank
    private String productStatusId;

    @Min(0)
    private Integer quantity;

    private Double regularPrice;

    private String sku;

    private Boolean taxable;

    @AllStringAndNotBlank
    private List<String> tagIds;
}
