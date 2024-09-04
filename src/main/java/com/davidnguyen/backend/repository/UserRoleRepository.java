package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    @Modifying
    @Query(value = "INSERT INTO user_roles (id, user_id, role_id) VALUES (:id, :userId, :roleId)", nativeQuery = true)
    @Transactional
    Integer assignRoleToUser(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);
}
