package com.warehouse.inventory_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "available_stock", nullable = false)
    private int availableStock;

    // Default constructor required by JPA
    public Inventory() {
    }

    public Inventory(String productCode, int availableStock) {
        this.productCode = productCode;
        this.availableStock = availableStock;
    }

    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}