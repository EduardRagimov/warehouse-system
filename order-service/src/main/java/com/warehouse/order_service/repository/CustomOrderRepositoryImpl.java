package com.warehouse.order_service.repository;

import com.warehouse.order_service.entity.Order;
import com.warehouse.order_service.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> searchOrdersDynamic(String productCode, OrderStatus status) {
        String jpql = "SELECT o FROM Order o WHERE 1=1";
        if (productCode != null) jpql += " AND o.productCode = :productCode";
        if (status != null) jpql += " AND o.status = :status";

        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        if (productCode != null) query.setParameter("productCode", productCode);
        if (status != null) query.setParameter("status", status);

        return query.getResultList();
    }
}