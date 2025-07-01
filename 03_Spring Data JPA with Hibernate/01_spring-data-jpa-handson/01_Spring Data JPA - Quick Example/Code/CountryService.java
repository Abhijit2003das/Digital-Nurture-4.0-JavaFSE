package com.cognizant.ormlearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;

@Service // Marks this class as a Spring Service component
public class CountryService {

    @Autowired // Injects an instance of CountryRepository
    private CountryRepository countryRepository;

    @Transactional // Ensures the entire method runs within a single database transaction
    public List<Country> getAllCountries() {
        // Invokes the findAll() method from JpaRepository to retrieve all countries
        return countryRepository.findAll();
    }
}