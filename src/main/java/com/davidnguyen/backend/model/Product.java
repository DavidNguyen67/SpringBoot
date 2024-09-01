package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "product_status_id", nullable = false)
    private String productStatusId;

    @Column(name = "regular_price", columnDefinition = "double default 0.0")
    private Double regularPrice;

    @Column(name = "discount_price", columnDefinition = "double default 0.0")
    private Double discountPrice;

    @Column(columnDefinition = "int default 0")
    private Integer quantity;

    @Column(columnDefinition = "boolean default false")
    private Boolean taxable;

}
