package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sessions")
@Data
@EqualsAndHashCode(callSuper = true)
public class Session extends BaseEntity {
    @Column
    private String data;
}
