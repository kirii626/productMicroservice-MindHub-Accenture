package com.mindhub.product_microservice.publishers;

import com.mindhub.product_microservice.events.StockReducedEmailEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String stockExchange;

    @Value("${rabbitmq.routing.stock.success}")
    private String stockSuccessRoutingKey;

    @Value("${rabbitmq.routing.stock.failure}")
    private String stockFailureRoutingKey;

    public void sendStockReducedEvent(Long orderId) {
        rabbitTemplate.convertAndSend(stockExchange, stockSuccessRoutingKey, orderId);
    }

    public void sendStockFailureEvent(Long orderId) {
        rabbitTemplate.convertAndSend(stockExchange, stockFailureRoutingKey, orderId);
    }

}
