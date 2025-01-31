package com.mindhub.product_microservice.services.implementations;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.events.external_dtos.OrderItemDtoInput;
import com.mindhub.product_microservice.exceptions.ProductNotFoundExc;
import com.mindhub.product_microservice.models.ProductEntity;
import com.mindhub.product_microservice.publishers.StockEventPublisher;
import com.mindhub.product_microservice.publishers.StockReducedEmailPublisher;
import com.mindhub.product_microservice.repositories.ProductRepository;
import com.mindhub.product_microservice.services.ProductService;
import com.mindhub.product_microservice.services.mappers.ProductMapper;
import com.mindhub.product_microservice.services.validations.ValidProductFields;
import com.mindhub.product_microservice.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ValidProductFields validProductFields;

    @Autowired
    private StockEventPublisher stockEventPublisher;

    @Autowired
    private StockReducedEmailPublisher stockReducedEmailPublisher;


    @Override
    public ApiResponse<List<ProductDtoOutput>> getAllProducts(HttpServletRequest request) {
        validProductFields.validateAdminRole(request);
        List<ProductDtoOutput> productDtoOutputList = productMapper.toDtoList(productRepository.findAll());

        ApiResponse<List<ProductDtoOutput>> response = new ApiResponse<>(
                "Products retrieved successfully",
                productDtoOutputList
        );

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<ProductDtoOutput>> createProduct(HttpServletRequest request, ProductDtoInput productDtoInput) {
        validProductFields.validateAdminRole(request);
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
    @Transactional
    public ResponseEntity<ApiResponse<ProductDtoOutput>> updateProduct(HttpServletRequest request, Long productId, ProductDtoInput productDtoInput) {
        validProductFields.validateAdminRole(request);
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
    @Transactional
    public boolean reduceStock(List<OrderItemDtoInput> orderItems, Long orderId) {
        try {
            for (OrderItemDtoInput item : orderItems) {
                ProductEntity product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ProductNotFoundExc("Product not found with ID: " + item.getProductId()));

                if (product.getStock() < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product ID: " + item.getProductId());
                }

                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);

                stockReducedEmailPublisher.sendStockReducedNotification(product.getId(), product.getName(), product.getStock());

            }

            stockEventPublisher.sendStockReducedEvent(orderId);
            return true;
        } catch (Exception e) {
            // 🔹 En caso de error, enviar evento de stock fallido
            stockEventPublisher.sendStockFailureEvent(orderId);
            return false;
        }
    }

    @Override
    public Double getProductPriceByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(ProductEntity::getPrice)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found with ID: "+ productId));
    }

    @Override
    public String getProductNameByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(ProductEntity::getName)
                .orElseThrow(() -> new ProductNotFoundExc("Product not found with ID: "+productId));
    }

    @Transactional
    private void updateProductFields(ProductEntity productEntity, ProductDtoInput productDtoInput) {
        productEntity.setName(productDtoInput.getName());
        productEntity.setDescription(productDtoInput.getDescription());
        productEntity.setPrice(productDtoInput.getPrice());
        productEntity.setStock(productDtoInput.getStock());
    }
}
