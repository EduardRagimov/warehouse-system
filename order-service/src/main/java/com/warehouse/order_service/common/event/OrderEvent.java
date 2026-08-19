package com.warehouse.order_service.common.event;

// Event Payload
public record OrderEvent(
        Long orderId,
        String skuCode,
        Integer quantity
) {}