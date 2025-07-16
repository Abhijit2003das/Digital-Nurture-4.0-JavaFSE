package com.example.productservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${product.message:Default Product Message}") // Get value from config server
    private String productMessage;

    @GetMapping("/{id}")
    public String getProduct(@PathVariable String id) {
        return productMessage + " - Product ID: " + id;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Product Service is Up and Running!";
    }
}