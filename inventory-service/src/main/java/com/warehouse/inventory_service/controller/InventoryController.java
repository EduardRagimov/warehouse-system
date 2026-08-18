package com.warehouse.inventory_service.controller;

import com.warehouse.inventory_service.dto.InventoryRequest;
import com.warehouse.inventory_service.entity.Inventory;
import com.warehouse.inventory_service.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public ResponseEntity<Inventory> addOrUpdateInventory(@RequestBody InventoryRequest request) {
        Inventory inventory = inventoryRepository.findBySkuCode(request.skuCode())
                .orElseGet(Inventory::new);

        inventory.setSkuCode(request.skuCode());
        inventory.setQuantity(request.quantity());
        inventory.setPrice(request.price());

        Inventory saved = inventoryRepository.save(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryRepository.findAll());
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<Inventory> getBySkuCode(@PathVariable String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}