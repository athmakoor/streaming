package com.streaming.service;

import com.streaming.bean.Country;
import com.streaming.bean.CountryOperatorsMap;

import java.util.List;

public interface CountryService {
    List<Country> getCountriesList();

    Country getCountryByLocale(String locale);

    CountryOperatorsMap getCountryOperatorsMapByLocale(String locale);

    CountryOperatorsMap getCountryOperatorsMapByOperatorCode(String operatorCode);
}
