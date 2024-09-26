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
    @Query("SELECT DISTINCT ur FROM UserRole ur WHERE ur.deletedAt IS NULL AND (ur.userId IN :userIds OR ur.roleId IN :roleIds)")
    List<UserRole> findUniqueUserRolesByRoleIdsOrUserIds(@Param("userIds") List<String> userIds,
                                                         @Param("roleIds") List<String> roleIds);

    @Query(value = "SELECT ur FROM UserRole ur WHERE ur.deletedAt IS NULL AND ur.roleId IN :roleIds")
    List<UserRole> findUserRolesByRoleIds(@Param("roleIds") List<String> roleIds);

    @Query(value = "SELECT ur FROM UserRole ur WHERE ur.deletedAt IS NULL AND ur.userId IN :userIds")
    List<UserRole> findUserRolesByUserIds(@Param("userIds") List<String> userIds);

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
