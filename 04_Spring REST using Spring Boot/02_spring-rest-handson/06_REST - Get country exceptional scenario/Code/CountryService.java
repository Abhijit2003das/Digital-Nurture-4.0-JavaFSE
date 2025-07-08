package com.cognizant.spring_learn.service;

import com.cognizant.spring_learn.Country;
import com.cognizant.spring_learn.service.exception.CountryNotFoundException;

public interface CountryService {
    Country getCountry(String code) throws CountryNotFoundException;
}
