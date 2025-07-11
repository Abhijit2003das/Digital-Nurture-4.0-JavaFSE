// src/main/java/com/example/countryservice/Employee.java
package com.example.countryservice;

import com.fasterxml.jackson.annotation.JsonFormat; // For date formatting
import jakarta.validation.Valid;                   // For validating nested objects
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date; // For dateOfBirth
import java.util.List; // For skills list

public class Employee {

    @NotNull(message = "Employee ID cannot be null")
    @Min(value = 1, message = "Employee ID should be a positive number")
    private Integer id;

    @NotNull(message = "Employee name cannot be null")
    @NotBlank(message = "Employee name cannot be blank")
    @Size(min = 1, max = 30, message = "Employee name must be between 1 and 30 characters")
    private String name;

    @NotNull(message = "Employee salary cannot be null")
    @Min(value = 0, message = "Employee salary must be zero or above")
    private Double salary;

    @NotNull(message = "Permanent status cannot be null")
    private Boolean permanent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST") // Added timezone for clarity
    @NotNull(message = "Date of Birth cannot be null")
    private Date dateOfBirth;

    @NotNull(message = "Department cannot be null")
    @Valid // This annotation is crucial: it tells the validator to also validate the nested Department object.
    private Department department;

    @NotNull(message = "Skills cannot be null")
    @Valid // This ensures each Skill object in the list is also validated.
    @Size(min = 1, message = "Employee must have at least one skill") // Example: ensure at least one skill
    private List<Skill> skills;

    // Default constructor is crucial for JSON deserialization
    public Employee() {
    }

    // Parameterized constructor (optional, for convenience)
    public Employee(Integer id, String name, Double salary, Boolean permanent, Date dateOfBirth, Department department, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.permanent = permanent;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
        this.skills = skills;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }

    public Boolean getPermanent() {
        return permanent;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", salary=" + salary +
               ", permanent=" + permanent +
               ", dateOfBirth=" + dateOfBirth +
               ", department=" + department +
               ", skills=" + skills +
               '}';
    }
}