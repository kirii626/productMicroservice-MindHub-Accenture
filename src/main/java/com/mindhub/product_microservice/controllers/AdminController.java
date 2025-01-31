package com.mindhub.product_microservice.controllers;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.services.ProductService;
import com.mindhub.product_microservice.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product-admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/test-headers")
    public ResponseEntity<String> testHeaders(HttpServletRequest request) {
        System.out.println("Endpoint alcanzado en product-microservice");

        request.getHeaderNames().asIterator().forEachRemaining(header ->
                System.out.println("Header recibido: " + header + " -> " + request.getHeader(header))
        );

        return ResponseEntity.ok("Headers revisados");
    }

    @GetMapping("/all")
    public ApiResponse<List<ProductDtoOutput>> getAllProducts(HttpServletRequest request) {
        return productService.getAllProducts(request);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct(
            HttpServletRequest request,
            @Valid @RequestBody ProductDtoInput productDtoInput) {
        return productService.createProduct(request, productDtoInput);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDtoOutput>> editProduct(
            HttpServletRequest request,
            @Valid @PathVariable Long productId,
            @RequestBody ProductDtoInput productDtoInput) {
        return productService.updateProduct(request, productId, productDtoInput);
    }

}
