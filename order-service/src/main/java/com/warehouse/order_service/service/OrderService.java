package com.warehouse.order_service.service;

import com.warehouse.order_service.common.config.RabbitMQConfig;
import com.warehouse.order_service.common.event.OrderEvent;
import com.warehouse.order_service.entity.Order;
import com.warehouse.order_service.entity.OrderStatus;
import com.warehouse.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order placeOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);

        // Publish Event to RabbitMQ
        OrderEvent event = new OrderEvent(savedOrder.getId(), savedOrder.getProductCode(), savedOrder.getQuantity());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);

        return savedOrder;
    }
}