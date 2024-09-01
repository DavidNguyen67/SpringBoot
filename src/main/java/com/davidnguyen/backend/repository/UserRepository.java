package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends Repository<User, String> {
    @Query(value = "SELECT u FROM User u ORDER BY u.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<User> findUsersWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT u from User u WHERE u.email = :email ORDER BY u.insertedAt ASC LIMIT :limit")
    List<User> findUsersByEmail(@Param("email") String email, @Param("limit") Integer limit);

    @Query(value = "SELECT count(*) FROM User u WHERE u.deletedAt IS NULL")
    Integer countUsers();

    @Modifying
    @Query(value = "INSERT INTO users (id, email, first_name, last_name, active) VALUES (:id, :email, :firstName, :lastName, :active)", nativeQuery = true)
    @Transactional
    Integer createAnUser(@Param("id") String id, @Param("email") String email, @Param("firstName") String firstName,
                         @Param("lastName") String lastName, @Param("active") Boolean active);

//    @Modifying
//    @Query(value = "UPDATE users SET ")

    @Modifying
    @Query(value = "DELETE FROM users where users.id IN :userId", nativeQuery = true)
    @Transactional
    Integer deleteUsers(@Param("userId") List<String> userIds);

}
