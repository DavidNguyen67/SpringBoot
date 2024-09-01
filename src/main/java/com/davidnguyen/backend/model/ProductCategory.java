package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends BaseEntity {
    @Column(name = "product_id")
    private String productId;

    @Column(name = "category_id")
    private String categoryId;
}
