package com.mindhub.product_microservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange}")
    private String stockExchange;

    @Value("${rabbitmq.queue.stock.reduce}")
    private String stockReduceQueue;

    @Value("${rabbitmq.queue.stock.success}")
    private String stockSuccessQueue;

    @Value("${rabbitmq.queue.stock.failure}")
    private String stockFailureQueue;

    @Value("${rabbitmq.queue.stock.notification}") // 🔹 Nueva cola para notificaciones
    private String stockNotificationQueue;

    @Value("${rabbitmq.routing.stock.reduce}")
    private String stockReduceRoutingKey;

    @Value("${rabbitmq.routing.stock.success}")
    private String stockSuccessRoutingKey;

    @Value("${rabbitmq.routing.stock.failure}")
    private String stockFailureRoutingKey;

    @Value("${rabbitmq.routing.stock.email}") // 🔹 Routing key para notificaciones de stock reducido
    private String stockReducedRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(stockExchange);
    }

    @Bean
    public Queue stockReduceQueue() {
        return new Queue(stockReduceQueue, true); // Durable
    }

    @Bean
    public Queue stockSuccessQueue() {
        return new Queue(stockSuccessQueue, true);
    }

    @Bean
    public Queue stockFailureQueue() {
        return new Queue(stockFailureQueue, true);
    }

    @Bean
    public Queue stockNotificationQueue() { // 🔹 Nueva cola para notificaciones
        return new Queue(stockNotificationQueue, true);
    }

    @Bean
    public Binding bindingStockReduce(Queue stockReduceQueue, TopicExchange stockExchange) {
        return BindingBuilder.bind(stockReduceQueue).to(stockExchange).with(stockReduceRoutingKey);
    }

    @Bean
    public Binding bindingStockSuccess(Queue stockSuccessQueue, TopicExchange stockExchange) {
        return BindingBuilder.bind(stockSuccessQueue).to(stockExchange).with(stockSuccessRoutingKey);
    }

    @Bean
    public Binding bindingStockFailure(Queue stockFailureQueue, TopicExchange stockExchange) {
        return BindingBuilder.bind(stockFailureQueue).to(stockExchange).with(stockFailureRoutingKey);
    }

    @Bean
    public Binding bindingStockNotification(Queue stockNotificationQueue, TopicExchange stockExchange) {
        return BindingBuilder.bind(stockNotificationQueue).to(stockExchange).with(stockReducedRoutingKey);
    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
