package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

public interface CountryService {
    Country findCountryByCode(String countryCode) throws CountryNotFoundException;

    @Transactional
    void addCountry(Country country);

    List<Country> getAllCountries();  // ✅ Add this
}
