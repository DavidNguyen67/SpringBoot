package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tag, String> {
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
}
