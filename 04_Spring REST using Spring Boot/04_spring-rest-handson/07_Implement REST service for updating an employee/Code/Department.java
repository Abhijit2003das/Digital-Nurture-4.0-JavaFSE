// src/main/java/com/example/countryservice/Department.java
package com.example.countryservice;

import jakarta.validation.constraints.Min;      // For numerical minimum constraint
import jakarta.validation.constraints.NotBlank;  // For string not blank constraint
import jakarta.validation.constraints.NotNull;   // For not null constraint
import jakarta.validation.constraints.Size;    // For string size constraint

public class Department {

    @NotNull(message = "Department ID cannot be null")
    @Min(value = 1, message = "Department ID should be a positive number") // Assuming IDs start from 1
    private Integer id;

    @NotNull(message = "Department name cannot be null")
    @NotBlank(message = "Department name cannot be blank")
    @Size(min = 1, max = 30, message = "Department name must be between 1 and 30 characters")
    private String name;

    // Default constructor is crucial for JSON deserialization
    public Department() {
    }

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id != null && id.equals(that.id); // Equality based on ID
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}