package com.warehouse.order_service.repository;

import com.warehouse.order_service.entity.Order;
import com.warehouse.order_service.entity.OrderStatus;

import java.util.List;

public interface CustomOrderRepository {
    List<Order> searchOrdersDynamic(String productCode, OrderStatus status);
}