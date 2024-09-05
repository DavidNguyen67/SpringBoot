package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RolesRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT r from Role r WHERE r.deletedAt IS NULL ORDER BY r.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<Role> findRolesWithPagination(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT count(*) FROM Role r WHERE r.deletedAt IS NULL")
    Integer countRoles();

    @Query(value = "SELECT r FROM Role r WHERE r.deletedAt IS NULL AND r.id IN :roleIds")
    List<Role> findRolesById(@Param("roleIds") List<String> roleIds);

    @Modifying
    @Query(value = "INSERT INTO roles (id, name) VALUES (:id, :name)", nativeQuery = true)
    @Transactional
    Integer createRole(@Param("id") String id, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE Role r SET r.deletedAt = current timestamp WHERE r.id IN :roleIds")
    @Transactional
    Integer deleteRolesById(@Param("roleIds") List<String> roleIds);


    @Query("SELECT r FROM Role r WHERE r.deletedAt <= :timestamp")
    List<Role> findAllByDeletedAtBefore(@Param("timestamp") LocalDateTime timestamp);

    @Modifying
    @Query(value = "DELETE FROM Role r WHERE r.deletedAt IS NOT NULL")
    Integer deleterOLESWhereDeletedAtIsNotNull();
}
