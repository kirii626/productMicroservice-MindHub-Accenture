package com.mindhub.product_microservice.events.external_dtos;

import jakarta.validation.constraints.NotBlank;

public class OrderItemDtoInput {

    private Long orderId;

    private Long productId;

    private Integer quantity;

    public OrderItemDtoInput() {
    }

    public OrderItemDtoInput(Long orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
