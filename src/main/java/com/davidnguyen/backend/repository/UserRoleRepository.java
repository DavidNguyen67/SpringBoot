package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    @Modifying
    @Query(value = "INSERT INTO user_roles (id, user_id, role_id) VALUES (:id, :userId, :roleId)", nativeQuery = true)
    @Transactional
    Integer assignRoleToUser(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);

    @Query(value = "SELECT ur FROM UserRole ur WHERE ur.deletedAt IS NULL AND ur.roleId IN :roleIds")
    List<UserRole> findUserRolesByRoleId(@Param("roleIds") List<String> roleIds);

    @Modifying
    @Query(value = "UPDATE UserRole ur SET ur.deletedAt = current timestamp WHERE ur.roleId IN :roleIds")
    @Transactional
    Integer deleteUserRolesByRoleIds(@Param("roleIds") List<String> roleIds);

    @Modifying
    @Query(value = "UPDATE UserRole ur SET ur.deletedAt = current timestamp WHERE ur.userId IN :userIds")
    @Transactional
    Integer deleteUserRolesByUserIds(@Param("userIds") List<String> userIds);

    @Query("SELECT ur FROM UserRole ur WHERE ur.deletedAt <= :timestamp")
    List<UserRole> findAllByDeletedAtBefore(@Param("timestamp") LocalDateTime timestamp);
}
