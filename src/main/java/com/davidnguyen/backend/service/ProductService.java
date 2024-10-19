package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateProductDTO;
import com.davidnguyen.backend.dto.DeleteProductDTO;
import com.davidnguyen.backend.dto.ProductDTO;
import com.davidnguyen.backend.dto.UpdateProductsDTO;
import com.davidnguyen.backend.model.Product;
import com.davidnguyen.backend.model.ProductTag;
import com.davidnguyen.backend.model.Tag;
import com.davidnguyen.backend.repository.ProductRepository;
import com.davidnguyen.backend.utility.helper.ObjectMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTagService productTagService;
    private final TagService tagService;

    public ProductService(ProductRepository productRepository, ProductTagService productTagService, TagService tagService) {
        this.productRepository = productRepository;
        this.productTagService = productTagService;
        this.tagService = tagService;
    }

    public Map<String, Object> findProductsWithPagination(Integer offset, Integer limit) {
        try {
            log.info("Fetching products with pagination: offset={}, limit={}", offset, limit);
            List<Product> products = productRepository.findProductsWithPagination(offset, limit);
            log.info("Found {} products", products.size());

            List<ProductDTO> productDTOs = ObjectMapperHelper.mapAll(products, ProductDTO.class);
            List<String> productIds = products.stream().map(Product::getId).toList();
            log.info("Product IDs: {}", productIds);

            List<ProductTag> productTags = productTagService.findUniqueProductTagsByProductIdsOrTagIds(productIds, null);
            log.info("Found {} product tags", productTags.size());

            List<String> tagIds = productTags.stream().map(ProductTag::getTagId).toList();
            List<Tag> tags = tagService.findTagsByIds(tagIds);
            log.info("Found {} tags", tags.size());

            if (!productTags.isEmpty()) {
                productDTOs.forEach(productDTO -> {
                    List<Tag> productTagsList = productTagService.getProductTags(productDTO.getId(), productTags, tags);
                    productDTO.setTags(productTagsList);
                    log.info("Product ID {} has {} tags", productDTO.getId(), productTagsList.size());
                });
            }

            Integer totalProducts = productRepository.countProducts();
            log.info("Total number of products: {}", totalProducts);

            return Map.of("products", productDTOs, "total", totalProducts);
        } catch (Exception e) {
            log.error("Error finding products: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> createAnProduct(CreateProductDTO createProductDTO) {
        try {
            Map<String, Integer> response = new HashMap<>();

            Integer resultCreateProduct = productRepository.createAnProduct(createProductDTO.getId(), createProductDTO.getDescription(), createProductDTO.getDiscountPrice(),
                    createProductDTO.getName(), createProductDTO.getProductStatusId(), createProductDTO.getQuantity(),
                    createProductDTO.getRegularPrice(), createProductDTO.getSku(), createProductDTO.getTaxable());

            if (resultCreateProduct == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product");
            }

            response.put("resultCreateProduct", resultCreateProduct);

            List<Tag> tags = tagService.findTagsByIds(createProductDTO.getTagIds());

            if (!tags.isEmpty()) {
                Integer resultMappingTags = productTagService.createProductTag(createProductDTO.getId(), createProductDTO.getTagIds());

                if (resultMappingTags == 0) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product tags");
                }

                response.put("resultMappingTags", resultMappingTags);
            }

            return response;
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Map<String, Integer> createProducts(List<CreateProductDTO> CreateProductDTOs) {
        try {
            Map<String, Integer> response = new HashMap<>();

            List<CreateProductDTO> productNewRecords = new ArrayList<>(CreateProductDTOs);
            List<Product> products = ObjectMapperHelper.mapAll(productNewRecords, Product.class);
            int resultCreateProduct = productRepository.saveAll(products).size();

            if (resultCreateProduct == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create products");
            }

            response.put("resultCreateProduct", resultCreateProduct);

            return response;
        } catch (Exception e) {
            log.error("Error creating products: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer deleteSoftProductByIds(DeleteProductDTO deleteProductDTO) {
        try {
            List<String> productIds = deleteProductDTO.getProductIds();

            List<Product> products = productRepository.findProductsByIds(productIds);

            if (products.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found");
            }


            Integer result = productRepository.deleteSoftProductByIds(deleteProductDTO.getProductIds());

            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete products");
            }

            return result;
        } catch (Exception e) {
            log.error("Error deleting products: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer updateProductByIds(UpdateProductsDTO updateProductsDTO) {
        try {
            List<String> productIds = updateProductsDTO.getProductIds();

            List<Product> products = productRepository.findProductsByIds(productIds);

            if (products.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found");
            }

            if (products.size() != productIds.size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Some products not found");
            }


            Integer result = productRepository.updateProductById(productIds, updateProductsDTO.getDescription(), updateProductsDTO.getDiscountPrice(),
                    updateProductsDTO.getName(), updateProductsDTO.getProductStatusId(), updateProductsDTO.getQuantity(),
                    updateProductsDTO.getRegularPrice(), updateProductsDTO.getSku(), updateProductsDTO.getTaxable());

            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update products");
            }

            return result;
        } catch (Exception e) {
            log.error("Error update products: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

}
