package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Entity
@Table(name = "cc_transactions")
@Data
@EqualsAndHashCode(callSuper = true)
public class CcTransaction extends BaseEntity {
    @Column
    private String code;

    @Column(name = "order_id", nullable = false) // thông qua khóa ngoại order_id
    private String orderId;

    @Column
    private Timestamp transdate;

    @Column(nullable = false)
    private String processor;

    @Column(name = "processor_trans_id", nullable = false)
    private String processorTransId;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "cc_num")
    private String ccNum;

    @Column(name = "cc_type")
    private String ccType;

    @Column
    private String response;

}
