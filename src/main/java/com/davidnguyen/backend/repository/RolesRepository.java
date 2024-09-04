package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, String> {
    @Query(value = "SELECT r from Roles r WHERE r.deletedAt IS NULL ORDER BY r.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<Roles> findRolesWithPagination(@Param("offset") Integer offset, @Param("limit") Integer limit);


    @Query(value = "SELECT count(*) FROM Roles r WHERE r.deletedAt IS NULL")
    Integer countRoles();

    @Modifying
    @Query(value = "INSERT INTO roles (id, name) VALUES (:id, :name)", nativeQuery = true)
    @Transactional
    Integer createRole(@Param("id") String id, @Param("name") String name);

}
