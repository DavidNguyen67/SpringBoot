package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT p from Product p WHERE p.deletedAt IS NULL ORDER BY p.insertedAt ASC LIMIT :limit OFFSET :offset")
    List<Product> findProductsWithPagination(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT count(*) FROM Product p WHERE p.deletedAt IS NULL")
    Integer countProducts();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO products (id, description, discount_price, name, product_status_id, quantity, regular_price, sku, taxable) VALUES (:id, :description, :discountPrice, :name, :productStatusId, :quantity, :regularPrice, :sku, :taxable)", nativeQuery = true)
    Integer createAnProduct(@Param("id") String id, @Param("description") String description, @Param("discountPrice") Double discountPrice,
                            @Param("name") String name, @Param("productStatusId") String productStatusId, @Param("quantity") Integer quantity,
                            @Param("regularPrice") Double regularPrice, @Param("sku") String sku, @Param("taxable") Boolean taxable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p SET p.deletedAt = current timestamp WHERE p.id IN :productIds")
    Integer deleteSoftProductByIds(@Param("productIds") List<String> productIds);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p " +
            "SET p.description = :description, p.discountPrice = :discountPrice, p.productStatusId = :productStatusId, p.quantity = :quantity, p.regularPrice = :regularPrice, p.sku = :sku, p.taxable = :taxable " +
            "WHERE p.id IN :productIds")
    Integer updateProductById(@Param("productIds") List<String> productIds, @Param("description") String description,
                              @Param("discountPrice") Double discountPrice, @Param("name") String name, @Param("productStatusId") String productStatusId,
                              @Param("quantity") Integer quantity, @Param("regularPrice") Double regularPrice, @Param("sku") String sku, @Param("taxable") Boolean taxable);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds AND p.deletedAt IS NULL")
    List<Product> findProductsByIds(@Param("productIds") List<String> productIds);


    @Query("SELECT p FROM Product p WHERE p.deletedAt <= :timestamp")
    List<Product> findAllByDeletedAtBefore(@Param("timestamp") LocalDateTime timestamp);
}
