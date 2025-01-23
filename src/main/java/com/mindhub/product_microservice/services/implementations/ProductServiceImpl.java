package com.mindhub.product_microservice.services.implementations;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.exceptions.ProductNotFoundExc;
import com.mindhub.product_microservice.models.ProductEntity;
import com.mindhub.product_microservice.repositories.ProductRepository;
import com.mindhub.product_microservice.services.ProductService;
import com.mindhub.product_microservice.services.mappers.ProductMapper;
import com.mindhub.product_microservice.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ApiResponse<List<ProductDtoOutput>> getAllProducts() {
        List<ProductDtoOutput> productDtoOutputList = productMapper.toDtoList(productRepository.findAll());

        ApiResponse<List<ProductDtoOutput>> response = new ApiResponse<>(
                "Products retrieved successfully",
                productDtoOutputList
        );

        return response;
    }

    @Override
    public ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct(ProductDtoInput productDtoInput) {
        ProductEntity productEntity = productMapper.toEntity(productDtoInput);
        ProductEntity savedProduct = productRepository.save(productEntity);
        ProductDtoOutput productDtoOutput = productMapper.toDto(savedProduct);

        ApiResponse<ProductDtoOutput> response = new ApiResponse<>(
                "Product created successfully",
                    productDtoOutput
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<ProductDtoOutput>> updateProduct(Long productId, ProductDtoInput productDtoInput) {
        if (productId == null || productId<=0) {
            throw new IllegalArgumentException("Invalid product ID: "+productId);
        }

        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found by ID: "+ productId));

        existingProduct.setName(productDtoInput.getName());
        existingProduct.setDescription(productDtoInput.getDescription());
        existingProduct.setPrice(productDtoInput.getPrice());
        existingProduct.setStock(productDtoInput.getStock());

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        ProductDtoOutput productDtoOutput = productMapper.toDto(updatedProduct);

        ApiResponse<ProductDtoOutput> response = new ApiResponse<>(
                "Product updated successfully",
                productDtoOutput
        );

        return ResponseEntity.ok(response);
    }
}
