package com.warehouse.order_service.controller;

import com.warehouse.order_service.common.event.OrderEvent;
import com.warehouse.order_service.dto.OrderRequest;
import com.warehouse.order_service.entity.Order;
import com.warehouse.order_service.entity.OrderStatus;
import com.warehouse.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderController(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        Order order = new Order();
        order.setSkuCode(request.skuCode());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        // Publish OrderCreatedEvent to RabbitMQ queue for inventory-service
        OrderEvent event = new OrderEvent(savedOrder.getId(), savedOrder.getSkuCode(), savedOrder.getQuantity());
        rabbitTemplate.convertAndSend("order_queue", event);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}