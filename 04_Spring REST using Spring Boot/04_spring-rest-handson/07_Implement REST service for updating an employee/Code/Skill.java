// src/main/java/com/example/countryservice/Skill.java
package com.example.countryservice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Skill {

    @NotNull(message = "Skill ID cannot be null")
    @Min(value = 1, message = "Skill ID should be a positive number") // Assuming IDs start from 1
    private Integer id;

    @NotNull(message = "Skill name cannot be null")
    @NotBlank(message = "Skill name cannot be blank")
    @Size(min = 1, max = 30, message = "Skill name must be between 1 and 30 characters")
    private String name;

    public Skill() {
    }

    public Skill(Integer id, String name) {
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
        return "Skill{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill that = (Skill) o;
        return id != null && id.equals(that.id); // Equality based on ID
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}