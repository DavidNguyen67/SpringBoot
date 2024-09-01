package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Entity
@Table(name = "sales_orders")
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesOrder extends BaseEntity {
    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Column(nullable = false)
    private Double total;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "coupon_id", nullable = false)
    private String couponId;

}
