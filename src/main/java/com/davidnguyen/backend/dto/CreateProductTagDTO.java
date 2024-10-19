package com.davidnguyen.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateProductTagDTO {
    private String id;
    private String productId;
    private String tagId;
}
