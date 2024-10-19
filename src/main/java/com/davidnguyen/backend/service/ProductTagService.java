package com.davidnguyen.backend.service;

import com.davidnguyen.backend.model.ProductTag;
import com.davidnguyen.backend.model.Tag;
import com.davidnguyen.backend.repository.ProductTagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductTagService {
    private final ProductTagsRepository productTagsRepository;

    public ProductTagService(ProductTagsRepository productTagsRepository) {
        this.productTagsRepository = productTagsRepository;
    }

    public List<ProductTag> findUniqueProductTagsByProductIdsOrTagIds(List<String> productIds, List<String> tagIds) {
        try {
            List<ProductTag> productTags = productTagsRepository.findUniqueProductTagsByProductIds(productIds);
            log.info("Found {} product tags for given productIds or tagIds", productTags.size());
            return productTags;
        } catch (Exception e) {
            log.error("Error getting productTags by productIds or tagIds: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Tag> getProductTags(String productId, List<ProductTag> productTagsMapping, List<Tag> allTags) {
        try {
            List<String> tagIdsInProductTags = productTagsMapping.stream().map(ProductTag::getTagId).toList();
            List<Tag> productTags = allTags.stream().filter(tag -> tagIdsInProductTags.contains(tag.getId())).toList();

            if (productTags.isEmpty()) {
                log.warn("No tags found for product: {}", productId);
            }

            log.info("Check productTags: {}", productTags.size());

            return productTags;
        } catch (Exception e) {
            log.error("Error getting productTags by productId: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ProductTag> findUniqueProductTagsByProductIds(List<String> productIds) {
        try {
            return productTagsRepository.findUniqueProductTagsByProductIds(productIds);
        } catch (Exception e) {
            log.error("Error getting productTags by productIds: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ProductTag> findUniqueProductTagsByTagIds(List<String> tagIds) {
        try {
            return productTagsRepository.findUniqueProductTagsByTagIds(tagIds);
        } catch (Exception e) {
            log.error("Error getting productTags by tagIds: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createProductTag(String productId, List<String> tagIds) {
        try {
            List<ProductTag> createProductTagDTO = new ArrayList<>();
            tagIds.forEach(tagId -> {
                ProductTag productTag = new ProductTag();
                productTag.setId(UUID.randomUUID().toString());
                productTag.setProductId(productId);
                productTag.setTagId(tagId);

                createProductTagDTO.add(productTag);
            });
            int resultCreateProductTag = productTagsRepository.saveAll(createProductTagDTO).size();

            if (resultCreateProductTag == 0) {
                throw new Exception("Failed to create productTag");
            }
            return resultCreateProductTag;

        } catch (Exception e) {
            log.error("Error creating productTag: {}", e.getMessage(), e);
            try {
                throw e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
