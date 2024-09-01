package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "user_roles")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;
}
