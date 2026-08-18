package com.warehouse.inventory_service.service;

import com.warehouse.inventory_service.dto.OrderEvent;
import com.warehouse.inventory_service.entity.Inventory;
import com.warehouse.inventory_service.repository.InventoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseService {

    private final InventoryRepository inventoryRepository;

    public WarehouseService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RabbitListener(queues = "order_queue")
    @Transactional
    public void handleOrderCreatedEvent(OrderEvent event) {
        System.out.println("Received order event for SKU: " + event.skuCode());

        Inventory item = inventoryRepository.findBySkuCode(event.skuCode())
                .orElseThrow(() -> new RuntimeException("Product not found for SKU: " + event.skuCode()));

        if (item.getQuantity() >= event.quantity()) {
            item.setQuantity(item.getQuantity() - event.quantity());
            inventoryRepository.save(item);
            System.out.println("Stock deducted successfully for order " + event.orderId() + ". Remaining: " + item.getQuantity());
        } else {
            System.err.println("Insufficient stock for order " + event.orderId() + ". Required: " + event.quantity() + ", Available: " + item.getQuantity());
            // Optional: Publish an OrderFailedEvent back to RabbitMQ for compensating transactions (Saga pattern)
        }
    }
}