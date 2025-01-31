package com.mindhub.product_microservice.events;

import com.mindhub.product_microservice.events.external_dtos.OrderItemDtoInput;

import java.util.List;

public class StockReduceEvent {

    private List<OrderItemDtoInput> orderItems;
    private Long orderId;

    public StockReduceEvent(List<OrderItemDtoInput> orderItems, Long orderId) {
        this.orderItems = orderItems;
        this.orderId = orderId;
    }

    public List<OrderItemDtoInput> getOrderItems() {
        return orderItems;
    }

    public Long getOrderId() {
        return orderId;
    }
}
