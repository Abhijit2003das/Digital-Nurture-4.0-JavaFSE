// src/main/java/com/example/orderservice/dto/OrderResponse.java
package com.example.orderservice.dto;

import com.example.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private UserDto userDetails; // This will hold user details from User Service

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.userId = order.getUserId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
    }
}