package com.cognizant.ormlearn.entity;

import javax.persistence.*;

@Entity
@Table(name = "user") // If your table name is 'user'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username; // ✅ Add this if not present

    // other fields and relationships (if any)

    // ✅ Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Other getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
