package com.example.customerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final Map<String, String> customers = new HashMap<>();

    public CustomerController() {
        customers.put("1", "Alice Smith");
        customers.put("2", "Bob Johnson");
        customers.put("3", "Charlie Brown");
    }

    @GetMapping
    public String getAllCustomers() {
        return "List of all customers: " + customers.values();
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable String id) {
        String customerName = customers.get(id);
        if (customerName != null) {
            return "Customer ID: " + id + ", Name: " + customerName;
        }
        return "Customer not found with ID: " + id;
    }
}