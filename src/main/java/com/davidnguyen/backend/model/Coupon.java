package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Entity
@Table(name = "coupons")
@Data
@EqualsAndHashCode(callSuper = true)
public class Coupon extends BaseEntity {
    @Column(nullable = false)
    private String code;

    @Column
    private String description;

    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    @Column
    private Double value;

    @Column(columnDefinition = "boolean default false")
    private Boolean multiple = false;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;
}
