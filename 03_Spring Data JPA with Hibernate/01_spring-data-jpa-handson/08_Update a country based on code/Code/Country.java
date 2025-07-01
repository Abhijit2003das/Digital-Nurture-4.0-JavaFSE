package com.cognizant.ormlearn.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

    @Id
    private String code;
    private String name;

    // Constructors
    public Country() {
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString
    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}
