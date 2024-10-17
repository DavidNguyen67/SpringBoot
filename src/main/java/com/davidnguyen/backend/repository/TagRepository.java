package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
    @Query(value = "SELECT t from Tag t WHERE t.deletedAt IS NULL ORDER BY t.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<Tag> findTagsWithPagination(Integer offset, Integer limit);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tags (id, name) VALUES (:id, :name)", nativeQuery = true)
    Integer createTag(String id, String name);

    @Modifying
    @Transactional
    @Query("UPDATE Tag t SET t.name = :newNameTag WHERE t.id IN :ids")
    Integer updateTagsName(List<String> ids, String newNameTag);

    @Query("SELECT t FROM Tag t WHERE t.name = :name")
    List<Tag> findTagsByName(String name);

    @Query("SELECT t FROM Tag t WHERE t.id IN :ids")
    List<Tag> findTagsByIds(List<String> ids);

    @Modifying
    @Transactional
    @Query("UPDATE Tag t SET t.deletedAt = current timestamp WHERE t.id IN :ids")
    Integer deleteSoftTagsProductByIds(List<String> ids);

    @Query("SELECT t FROM Tag t WHERE t.deletedAt <= :timestamp")
    List<Tag> findAllByDeletedAtBefore(@Param("timestamp") LocalDateTime timestamp);
}
