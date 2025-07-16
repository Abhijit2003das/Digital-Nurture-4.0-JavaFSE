package com.example.downstreamservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping; // Remove this import if not used elsewhere
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple REST controller for the downstream service.
 * This controller simulates a service that occasionally fails,
 * allowing us to test the API Gateway's circuit breaker.
 */
@RestController
// Remove or comment out this line:
// @RequestMapping("/my-service")
public class DownstreamServiceController {

    // Counter to simulate failures every few requests
    private final AtomicInteger requestCount = new AtomicInteger(0);

    /**
     * Endpoint to return some data. It will simulate a failure every 3rd request.
     *
     * @return A ResponseEntity with a success message or an error status.
     */
    @GetMapping("/data") // This will now map to /data directly
    public ResponseEntity<String> getData() {
        int currentCount = requestCount.incrementAndGet();
        System.out.println("Downstream Service: Request received. Count: " + currentCount);

        // Simulate a failure for every 3rd request (adjust as needed)
        if (currentCount % 3 == 0) {
            System.out.println("Downstream Service: Simulating failure (HTTP 500).");
            // Reset count to prevent it from growing indefinitely
            if (currentCount > 100) {
                requestCount.set(0);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Simulated Internal Server Error");
        } else {
            System.out.println("Downstream Service: Success (HTTP 200).");
            return ResponseEntity.ok("Data from Downstream Service - Success!");
        }
    }
}