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
        System.out.println("Received order for product: " + event.productCode());

        Inventory item = inventoryRepository.findById(event.productCode())
                .orElseThrow(() -> new RuntimeException("Product not found in warehouse"));

        if (item.getAvailableStock() >= event.quantity()) {
            item.setAvailableStock(item.getAvailableStock() - event.quantity());
            inventoryRepository.save(item);
            System.out.println("Stock updated successfully for order " + event.orderId());
        }
        else {
            System.err.println("Insufficient stock for order " + event.orderId());
            // Here you could publish an "OrderFailedEvent" back to RabbitMQ
        }
    }
}