package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country; // Import your Country model

@Repository // Marks this interface as a Spring Data JPA repository
public interface CountryRepository extends JpaRepository<Country, String> {
    // JpaRepository provides methods like findAll(), findById(), save(), delete() etc.
    // <Country, String> -> Country is the entity type, String is the type of its primary key (code)
}