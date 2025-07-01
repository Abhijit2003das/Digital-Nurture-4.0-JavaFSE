package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

import java.util.List;

public interface CountryService {

    Country findCountryByCode(String code) throws CountryNotFoundException;

    void addCountry(Country country);

    void updateCountry(String code, String name) throws CountryNotFoundException;

    List<Country> getAllCountries();  // ✅ Add this method
}
