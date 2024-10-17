package com.davidnguyen.backend.controller;

import com.davidnguyen.backend.dto.CreateProductDTO;
import com.davidnguyen.backend.dto.DeleteProductDTO;
import com.davidnguyen.backend.dto.UpdateProductsDTO;
import com.davidnguyen.backend.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Map<String, Object> findProductsWithPagination(@RequestParam("offset") @Min(0) Integer offset, @RequestParam("limit") @Min(1) @Max(50) Integer limit) {
        return productService.findProductsWithPagination(offset, limit);
    }

    @PostMapping("/create/product")
    public Integer createProducts(@Valid @RequestBody @Size List<CreateProductDTO> createProductDTO) {
        if (createProductDTO.size() > 1) {
            return productService.createProducts(createProductDTO);
        }
        return productService.createAnProduct(createProductDTO.get(0));
    }


    @PutMapping("/update/products")
    public Integer updateProducts(@Valid @RequestBody UpdateProductsDTO updateProductsDTO) {
        return productService.updateProductByIds(updateProductsDTO);
    }

    @DeleteMapping("/delete/products")
    public Integer deleteProducts(@Valid @RequestBody DeleteProductDTO deleteProductDTO) {
        return productService.deleteSoftProductByIds(deleteProductDTO);
    }
}
