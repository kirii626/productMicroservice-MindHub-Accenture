package com.mindhub.product_microservice.services;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ApiResponse<List<ProductDtoOutput>> getAllProducts();

    ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct( ProductDtoInput productDtoInput);

    ResponseEntity<ApiResponse<ProductDtoOutput>> updateProduct(@Valid Long productId, ProductDtoInput productDtoInput);

    boolean validateStock(Long productId, Integer quantity);

    void reduceStock(Long productId, Integer quantity);

    Double getProductPriceByProductId(Long productId);

    String getProductNameByProductId(Long productId);
}
