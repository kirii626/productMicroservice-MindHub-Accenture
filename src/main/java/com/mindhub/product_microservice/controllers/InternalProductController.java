package com.mindhub.product_microservice.controllers;

import com.mindhub.product_microservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/product")
public class InternalProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}/validate-stock")
    public ResponseEntity<Boolean> validateStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        boolean isValid = productService.validateStock(productId, quantity);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("{productId}/price")
    public ResponseEntity<Double> getProductPrice(@PathVariable Long productId) {
        Double price = productService.getProductPriceByProductId(productId);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/{productId}/name")
    public ResponseEntity<String> getProductName(@PathVariable Long productId) {
        String name = productService.getProductNameByProductId(productId);
        return ResponseEntity.ok(name);
    }
}
