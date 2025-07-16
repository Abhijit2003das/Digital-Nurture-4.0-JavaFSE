package com.example.paymentservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j // Lombok annotation for logging
public class PaymentService {

    private static final String PAYMENT_CIRCUIT_BREAKER = "paymentService";
    private int failureCount = 0; // To simulate failures

    @CircuitBreaker(name = PAYMENT_CIRCUIT_BREAKER, fallbackMethod = "processPaymentFallback")
    public String processPayment(String orderId) {
        log.info("Attempting to process payment for order: {}", orderId);

        // Simulate a slow third-party API call
        try {
            Thread.sleep(500); // Simulate network latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted during payment processing", e);
            throw new RuntimeException("Payment processing interrupted", e);
        }

        // Simulate API failures (e.g., 60% failure rate)
        if (new Random().nextInt(10) < 6) { // 6 out of 10 times, it will fail
            failureCount++;
            log.warn("Payment processing failed for order {}. Failure count: {}", orderId, failureCount);
            throw new RuntimeException("Third-party payment API failed for order: " + orderId);
        }

        failureCount = 0; // Reset on success
        log.info("Payment successfully processed for order: {}", orderId);
        return "Payment processed successfully for order: " + orderId;
    }

    public String processPaymentFallback(String orderId, Throwable t) {
        log.error("--- FALLBACK INITIATED for order {} --- Reason: {}", orderId, t.getMessage());
        // Log additional details about the fallback for monitoring
        if (t instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            log.warn("Circuit Breaker is OPEN for paymentService. Not permitting calls for order: {}", orderId);
        } else {
            log.error("Generic fallback triggered for order {}. Original error type: {}", orderId, t.getClass().getSimpleName());
        }
        return "Payment processing temporarily unavailable for order: " + orderId + ". Please try again later. (Fallback)";
    }
}