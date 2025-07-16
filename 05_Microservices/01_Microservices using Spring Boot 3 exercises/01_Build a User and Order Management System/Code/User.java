// src/main/java/com/example/userservice/model/User.java
package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marks this class as a JPA entity
@Table(name = "users") // Specifies the table name in the database
@Data // From Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // From Lombok: Generates a no-argument constructor
@AllArgsConstructor // From Lombok: Generates a constructor with all fields
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    @Column(nullable = false, unique = true) // Ensures username is not null and unique
    private String username;

    @Column(nullable = false) // Ensures email is not null
    private String email;

    private String firstName;
    private String lastName;
}