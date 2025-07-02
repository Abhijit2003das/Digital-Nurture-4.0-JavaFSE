package com.cognizant.ormlearn.service.impl;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country findCountryByCode(String code) {
        return countryRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Country not found with code: " + code));
    }

    @Override
    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    @Transactional
    public void updateCountry(String code, String name) {
        Country country = countryRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Country not found with code: " + code));
        country.setName(name);
        countryRepository.save(country);
    }

    @Override
    @Transactional
    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }

    @Override
    public List<Country> searchByNameContaining(String keyword) {
        return countryRepository.findByNameContaining(keyword);
    }

    @Override
    public List<Country> searchByNameContainingOrderByNameAsc(String keyword) {
        return countryRepository.findByNameContainingOrderByNameAsc(keyword);
    }

    @Override
    public List<Country> searchByNameStartingWith(String prefix) {
        return countryRepository.findByNameStartingWith(prefix);
    }
}
