package com.warehouse.inventory_service.dto;

public record OrderEvent(
        Long orderId,
        String skuCode,
        Integer quantity
) {}