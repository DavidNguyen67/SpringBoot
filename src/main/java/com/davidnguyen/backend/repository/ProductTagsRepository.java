package com.davidnguyen.backend.repository;

import com.davidnguyen.backend.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagsRepository extends JpaRepository<ProductTag, String> {
}
