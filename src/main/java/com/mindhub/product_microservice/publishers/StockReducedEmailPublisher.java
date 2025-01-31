package com.mindhub.product_microservice.publishers;

import com.mindhub.product_microservice.events.StockReducedEmailEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockReducedEmailPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String stockExchange;

    @Value("${rabbitmq.routing.stock.email}")
    private String stockReducedRoutingKey;

    public void sendStockReducedNotification(Long productId, String productName, Integer newStock) {
        StockReducedEmailEvent event = new StockReducedEmailEvent(productId, productName, newStock);
        rabbitTemplate.convertAndSend(stockExchange, stockReducedRoutingKey, event);
    }
}
