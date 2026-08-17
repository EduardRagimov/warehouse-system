package com.warehouse.inventory_service.repository;

import com.warehouse.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    // Standard CRUD methods (findById, save, delete, etc.) are inherited from JpaRepository.
    // The primary key ID type is String corresponding to the productCode field on Inventory.
}