package com.mindhub.product_microservice.services.mappers;

import com.mindhub.product_microservice.dtos.ProductDtoInput;
import com.mindhub.product_microservice.dtos.ProductDtoOutput;
import com.mindhub.product_microservice.models.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDtoOutput toDto(ProductEntity productEntity) {
        return new ProductDtoOutput(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStock()
        );
    }

    public List<ProductDtoOutput> toDtoList(List<ProductEntity> productEntities) {
        return productEntities
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductEntity toEntity(ProductDtoInput productDtoInput) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDtoInput.getName());
        productEntity.setDescription(productDtoInput.getDescription());
        productEntity.setPrice(productDtoInput.getPrice());
        productEntity.setStock(productDtoInput.getStock());
        return productEntity;
    }
}
