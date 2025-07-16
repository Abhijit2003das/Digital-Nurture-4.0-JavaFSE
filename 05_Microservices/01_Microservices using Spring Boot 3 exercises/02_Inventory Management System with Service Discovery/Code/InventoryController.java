package com.example.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Value("${inventory.message:Default Inventory Message}") // Get value from config server
    private String inventoryMessage;

    @GetMapping("/{productId}/stock")
    public String getStock(@PathVariable String productId) {
        // Simulate stock check
        int stock = (int) (Math.random() * 100) + 1; // Random stock between 1 and 100
        return inventoryMessage + " - Product ID: " + productId + ", Stock: " + stock;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Inventory Service is Up and Running!";
    }
}