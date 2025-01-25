package com.mindhub.product_microservice.services.validations;

import com.mindhub.product_microservice.exceptions.ProductNotFoundExc;
import com.mindhub.product_microservice.models.ProductEntity;
import com.mindhub.product_microservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidProductFields {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity validateAndGetProduct(Long productId) {
        validateProductId(productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found by ID: " + productId));
    }

    public void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID: " + productId);
        }
    }


}
