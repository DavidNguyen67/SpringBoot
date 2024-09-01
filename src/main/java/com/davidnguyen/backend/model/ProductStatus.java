package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_statuses")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductStatus extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
