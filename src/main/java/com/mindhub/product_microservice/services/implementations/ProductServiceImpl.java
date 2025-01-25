package com.mindhub.product_microservice.services.implementations;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.exceptions.ProductNotFoundExc;
import com.mindhub.product_microservice.models.ProductEntity;
import com.mindhub.product_microservice.repositories.ProductRepository;
import com.mindhub.product_microservice.services.ProductService;
import com.mindhub.product_microservice.services.mappers.ProductMapper;
import com.mindhub.product_microservice.services.validations.ValidProductFields;
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

    @Autowired
    private ValidProductFields validProductFields;


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
        ProductEntity existingProduct = validProductFields.validateAndGetProduct(productId);

        updateProductFields(existingProduct, productDtoInput);
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        ProductDtoOutput productDtoOutput = productMapper.toDto(updatedProduct);

        ApiResponse<ProductDtoOutput> response = new ApiResponse<>(
                "Product updated successfully",
                productDtoOutput
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public boolean validateStock(Long productId, Integer quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found with ID: " + productId));
        return product.getStock() >= quantity;
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found with ID: " + productId));
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    private void updateProductFields(ProductEntity productEntity, ProductDtoInput productDtoInput) {
        productEntity.setName(productDtoInput.getName());
        productEntity.setDescription(productDtoInput.getDescription());
        productEntity.setPrice(productDtoInput.getPrice());
        productEntity.setStock(productDtoInput.getStock());
    }
}
