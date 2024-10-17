package com.davidnguyen.backend.service;

import com.davidnguyen.backend.dto.CreateProductDTO;
import com.davidnguyen.backend.dto.DeleteProductDTO;
import com.davidnguyen.backend.dto.UpdateProductsDTO;
import com.davidnguyen.backend.model.Product;
import com.davidnguyen.backend.repository.ProductRepository;
import com.davidnguyen.backend.utility.helper.ObjectMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<String, Object> findProductsWithPagination(Integer offset, Integer limit) {
        try {
            List<Product> products = productRepository.findProductsWithPagination(offset, limit);
            Integer total = productRepository.countProducts();

            return Map.of("products", products, "total", total);
        } catch (Exception e) {
            log.error("Error finding products: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createAnProduct(CreateProductDTO createProductDTO) {
        try {
            Integer result = productRepository.createAnProduct(createProductDTO.getId(), createProductDTO.getDescription(), createProductDTO.getDiscountPrice(),
                    createProductDTO.getName(), createProductDTO.getProductStatusId(), createProductDTO.getQuantity(),
                    createProductDTO.getRegularPrice(), createProductDTO.getSku(), createProductDTO.getTaxable());

            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product");
            }

            return result;
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage(), e);
            throw e; // rethrow the exception after logging it
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer createProducts(List<CreateProductDTO> CreateProductDTOs) {
        try {
            List<CreateProductDTO> productNewRecords = new ArrayList<>(CreateProductDTOs);
            List<Product> products = ObjectMapperHelper.mapAll(productNewRecords, Product.class);
            int result = productRepository.saveAll(products).size();

            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create products");
            }
            return result;
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
