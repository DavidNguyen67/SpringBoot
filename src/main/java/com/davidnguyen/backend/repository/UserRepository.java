package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT u FROM User u WHERE u.deletedAt IS NULL ORDER BY u.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<User> findUsersWithPagination(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT count(*) FROM User u " +
            "WHERE u.deletedAt IS NULL")
    Integer countUsers();

    @Query(value = "SELECT u from User u " +
            "WHERE u.email = :email " +
            "ORDER BY u.insertedAt ASC " +
            "LIMIT :limit")
    List<User> findUsersByEmail(@Param("email") String email, @Param("limit") Integer limit);

    @Query(value = "SELECT u FROM User u " +
            "WHERE u.id IN :userIds AND u.deletedAt IS NULL " +
            "ORDER BY u.insertedAt ASC")
    List<User> findUsersById(@Param("userIds") List<String> userIds);

    @Modifying
    @Query(value = "INSERT INTO users (id, email, first_name, last_name, active) VALUES (:id, :email, :firstName, :lastName, :active)", nativeQuery = true)
    @Transactional
    Integer createAnUser(@Param("id") String id, @Param("email") String email, @Param("firstName") String firstName,
                         @Param("lastName") String lastName, @Param("active") Boolean active);

    @Modifying
    @Query(value = "UPDATE User u SET u.deletedAt = current timestamp WHERE u.id IN :userIds")
    @Transactional
    Integer deleteUsersById(@Param("userIds") List<String> userIds);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User u " +
            "SET u.email = :email, u.firstName = :firstName, u.lastName = :lastName, u.active = :active " +
            "WHERE u.id IN :userIds")
    Integer updateUsersById(@Param("userIds") List<String> userId, @Param("email") String email,
                            @Param("firstName") String firstName, @Param("lastName") String lastName,
                            @Param("active") Boolean active);


    @Query("SELECT u FROM User u WHERE u.deletedAt <= :timestamp")
    List<User> findAllByDeletedAtBefore(@Param("timestamp") LocalDateTime timestamp);
}
