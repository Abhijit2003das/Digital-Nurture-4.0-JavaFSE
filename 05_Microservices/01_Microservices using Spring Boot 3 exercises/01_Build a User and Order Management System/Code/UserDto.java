// src/main/java/com/example/orderservice/dto/UserDto.java
package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    // Add other fields from User entity if needed for display/validation
}