package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    public UserRole(@NotBlank String id, @NotBlank(message = "{messages.userIdRequired}") String userId,
                    @NotBlank(message = "{messages.roleIdRequired}") String roleId) {
        super(id);
        this.userId = userId;
        this.roleId = roleId;
    }


    public UserRole() {
    }
}
