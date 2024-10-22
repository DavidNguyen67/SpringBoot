package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.CreateProductDTO;
import com.davidnguyen.backend.dto.DeleteProductDTO;
import com.davidnguyen.backend.dto.UpdateProductsDTO;
import com.davidnguyen.backend.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> findProductsWithPagination(@Valid @RequestParam("offset") @Min(0) Integer offset, @Valid @RequestParam("limit") @Min(1) @Max(50) Integer limit) {
        return ResponseEntity.ok(productService.findProductsWithPagination(offset, limit));
    }

    @PostMapping("/create/product")
    public ResponseEntity<Map<String, Integer>> createProducts(@Valid @Size @RequestBody List<CreateProductDTO> createProductDTO) {
        log.info("Received request to create products, number of products: {}", createProductDTO.size());
        if (createProductDTO.size() > 1) {
            log.info("Creating multiple products");
            return ResponseEntity.ok(productService.createProducts(createProductDTO));
        }
        log.info("Creating a single product");
        return ResponseEntity.ok(productService.createAnProduct(createProductDTO.get(0)));
    }

    @PutMapping("/update/products")
    public ResponseEntity<HashMap<String, Integer>> updateProducts(@Valid @RequestBody UpdateProductsDTO updateProductsDTO) {
        return ResponseEntity.ok(productService.updateProductByIds(updateProductsDTO));
    }

    @DeleteMapping("/delete/products")
    public ResponseEntity<Integer> deleteProducts(@Valid @RequestBody DeleteProductDTO deleteProductDTO) {
        return ResponseEntity.ok(productService.deleteSoftProductByIds(deleteProductDTO));
    }
}
