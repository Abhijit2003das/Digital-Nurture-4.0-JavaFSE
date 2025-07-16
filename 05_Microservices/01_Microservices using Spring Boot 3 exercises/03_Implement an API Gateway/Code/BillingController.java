package com.example.billingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bills")
public class BillingController {

    private final Map<String, String> bills = new HashMap<>();

    public BillingController() {
        bills.put("C1", "Bill for Alice - $120.00");
        bills.put("C2", "Bill for Bob - $75.50");
        bills.put("C3", "Bill for Charlie - $200.00");
    }

    @GetMapping
    public String getAllBills() {
        return "List of all bills: " + bills.values();
    }

    @GetMapping("/{customerId}")
    public String getBillByCustomerId(@PathVariable String customerId) {
        String billInfo = bills.get(customerId);
        if (billInfo != null) {
            return "Bill for Customer ID: " + customerId + " - " + billInfo;
        }
        return "No bill found for Customer ID: " + customerId;
    }
}