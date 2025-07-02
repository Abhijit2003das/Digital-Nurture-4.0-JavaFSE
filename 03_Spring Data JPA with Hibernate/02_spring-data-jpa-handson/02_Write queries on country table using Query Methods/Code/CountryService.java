package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country findCountryByCode(String code);

    void addCountry(Country country);

    void updateCountry(String code, String name);

    void deleteCountry(String code);

    List<Country> searchByNameContaining(String keyword);

    List<Country> searchByNameContainingOrderByNameAsc(String keyword);

    List<Country> searchByNameStartingWith(String prefix);
}
