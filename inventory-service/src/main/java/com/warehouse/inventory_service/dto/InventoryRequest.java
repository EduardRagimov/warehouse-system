package com.warehouse.inventory_service.dto;

import java.math.BigDecimal;

public record InventoryRequest(
        String skuCode,
        Integer quantity,
        BigDecimal price
) {}