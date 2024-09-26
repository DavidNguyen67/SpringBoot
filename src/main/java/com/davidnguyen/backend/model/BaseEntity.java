package com.davidnguyen.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * Annotation của JPA để chỉ ra rằng lớp này là lớp cha mà các lớp thực thể khác
 * có thể kế thừa.
 */
@Data
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    public BaseEntity(String id) {
        this.id = id;
    }

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
