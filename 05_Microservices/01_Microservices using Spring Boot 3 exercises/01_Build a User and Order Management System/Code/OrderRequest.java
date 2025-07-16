// src/main/java/com/example/orderservice/dto/OrderRequest.java
package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    // Potentially a list of items later, but keep it simple for now.
}