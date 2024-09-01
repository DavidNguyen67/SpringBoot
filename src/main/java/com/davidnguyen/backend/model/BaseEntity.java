package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * Annotation của JPA để chỉ ra rằng lớp này là lớp cha mà các lớp thực thể khác
 * có thể kế thừa.
 */
@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    private String id;

    @CreationTimestamp
    @Column(name = "inserted_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insertedAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", nullable = true, columnDefinition = "TIMESTAMP")
    private Timestamp deletedAt;
}
