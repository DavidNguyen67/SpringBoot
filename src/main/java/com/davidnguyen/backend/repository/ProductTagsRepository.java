package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductTagsRepository extends JpaRepository<ProductTag, String> {
    @Query("SELECT DISTINCT pt FROM ProductTag pt WHERE pt.deletedAt IS NULL AND (pt.productId IN :productIds OR pt.tagId IN :tagIds)")
    List<ProductTag> findUniqueProductTagsByProductIdsOrTagIds(@Param("productIds") List<String> productIds, @Param("tagIds") List<String> tagIds);

    @Query("SELECT DISTINCT pt FROM ProductTag pt WHERE pt.deletedAt IS NULL AND pt.productId IN :productIds")
    List<ProductTag> findUniqueProductTagsByProductIds(@Param("productIds") List<String> productIds);

    @Query("SELECT DISTINCT pt FROM ProductTag pt WHERE pt.deletedAt IS NULL AND pt.tagId IN :tagIds")
    List<ProductTag> findUniqueProductTagsByTagIds(@Param("tagIds") List<String> tagIds);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_tags (product_id, tag_id) VALUES (:productId, :tagId)", nativeQuery = true)
    Integer createProductTag(@Param("productId") String productId, @Param("tagId") String tagId);
}
