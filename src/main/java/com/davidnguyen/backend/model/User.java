package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Column(unique = true)
    private String email;

    @Column(name = "first_name", nullable = false, unique = false)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = false)
    private String lastName;

    @Column(columnDefinition = "boolean default true")
    private Boolean active;
}
