package com.mindhub.product_microservice.services;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.events.external_dtos.OrderItemDtoInput;
import com.mindhub.product_microservice.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ApiResponse<List<ProductDtoOutput>> getAllProducts(HttpServletRequest request);

    ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct( HttpServletRequest request, ProductDtoInput productDtoInput);

    ResponseEntity<ApiResponse<ProductDtoOutput>> updateProduct(HttpServletRequest request, Long productId, ProductDtoInput productDtoInput);

    boolean validateStock(Long productId, Integer quantity);

    boolean reduceStock(List<OrderItemDtoInput> orderItems, Long orderId);

    Double getProductPriceByProductId(Long productId);

    String getProductNameByProductId(Long productId);
}
