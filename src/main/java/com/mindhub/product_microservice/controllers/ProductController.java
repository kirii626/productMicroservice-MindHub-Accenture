package com.mindhub.product_microservice.controllers;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.services.ProductService;
import com.mindhub.product_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ApiResponse<List<ProductDtoOutput>> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct(@Valid @RequestBody ProductDtoInput productDtoInput) {
        return productService.createProduct(productDtoInput);
    }

    @PutMapping("/productId")
    public ResponseEntity<ApiResponse<ProductDtoOutput>> editProduct(@Valid @PathVariable Long productId,
                                                                     @RequestBody ProductDtoInput productDtoInput) {
        return productService.updateProduct(productId, productDtoInput);
    }
}
