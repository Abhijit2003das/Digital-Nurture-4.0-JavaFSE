// src/main/java/com/example/orderservice/OrderServiceApplication.java
package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient; // Import WebClient

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean // This method creates and configures a WebClient bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}