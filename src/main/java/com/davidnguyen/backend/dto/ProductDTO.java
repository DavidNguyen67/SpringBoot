package com.davidnguyen.backend.dto;

import com.davidnguyen.backend.model.Product;
import com.davidnguyen.backend.model.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO extends Product {
    private List<Tag> tags;
}
