package com.mindhub.product_microservice.listeners;

import com.mindhub.product_microservice.events.StockReduceEvent;
import com.mindhub.product_microservice.publishers.StockEventPublisher;
import com.mindhub.product_microservice.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StockEventListener {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockEventPublisher stockEventPublisher;

    @RabbitListener(queues = "${rabbitmq.queue.stock.reduce}")
    public void handleStockReduction(StockReduceEvent event) {
        boolean success = productService.reduceStock(event.getOrderItems(), event.getOrderId());

    }
}
