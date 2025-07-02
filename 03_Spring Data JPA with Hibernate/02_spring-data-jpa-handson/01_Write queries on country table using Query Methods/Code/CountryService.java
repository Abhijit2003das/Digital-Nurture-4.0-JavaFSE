package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

import java.util.List;

public interface CountryService {
    Country findCountryByCode(String code) throws CountryNotFoundException;
    void addCountry(Country country);
    void updateCountry(String code, String name) throws CountryNotFoundException;
    void deleteCountry(String code);
    List<Country> getCountriesContaining(String substring);
    List<Country> getCountriesContainingOrderByAsc(String substring);
    List<Country> getCountriesStartingWith(String prefix);

    // ✅ Add this
    List<Country> getAllCountries();
}
