package com.example.paymentservice.controller;

import com.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/process/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable String orderId) {
        String result = paymentService.processPayment(orderId);
        return ResponseEntity.ok(result);
    }
}