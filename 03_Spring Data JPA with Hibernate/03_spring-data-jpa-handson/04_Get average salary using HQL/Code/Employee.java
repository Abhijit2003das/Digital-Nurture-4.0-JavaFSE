package com.cognizant.ormlearn.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {

    @Id
    private int id;

    private String name;
    private double salary;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private boolean permanent;

    @ManyToOne
    @JoinColumn(name = "em_dp_id") // Foreign key column in Employee table
    private Department department;

    @ManyToMany
    @JoinTable(name = "employee_skill",
            joinColumns = @JoinColumn(name = "es_em_id"),
            inverseJoinColumns = @JoinColumn(name = "es_sk_id"))
    private List<Skill> skillList;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id +
               ", name=" + name +
               ", salary=" + salary +
               ", permanent=" + permanent +
               ", dateOfBirth=" + dateOfBirth +
               ", department=" + department +
               ", skills=" + skillList + "]";
    }
}
