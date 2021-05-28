package com.streaming.service.impl;

import com.streaming.bean.Country;
import com.streaming.bean.CountryOperatorsMap;
import com.streaming.bean.Operator;
import com.streaming.bean.jpa.CountryEntity;
import com.streaming.exception.ItemNotFoundException;
import com.streaming.repository.CountryRepository;
import com.streaming.service.CountryService;
import com.streaming.service.OperatorService;
import com.streaming.service.mapping.ServiceMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CountryServiceImpl implements CountryService {
    @Resource
    private ServiceMapper<Country, CountryEntity> countryServiceMapper;

    @Resource
    private CountryRepository countryRepository;

    @Resource
    private OperatorService operatorService;

    public List<Country> getCountriesList() {
        List<Country> countries = new ArrayList<>();
        List<CountryEntity> countryEntities = countryRepository.findByActiveTrue();

        for (CountryEntity entity : countryEntities) {
            countries.add(countryServiceMapper.mapEntityToDTO(entity, Country.class));
        }

        return countries;
    }

    @Override
    public Country getCountryByLocale(String locale) {
        Optional<CountryEntity> optionalCountryEntity = countryRepository.findByLocaleAndActiveTrue(locale);

        if (!optionalCountryEntity.isPresent()) {
            throw new ItemNotFoundException("Invalid locale -" + locale);
        }

        return countryServiceMapper.mapEntityToDTO(optionalCountryEntity.get(), Country.class);
    }

    @Override
    public CountryOperatorsMap getCountryOperatorsMapByLocale(String locale) {
        CountryOperatorsMap map = new CountryOperatorsMap();
        Optional<CountryEntity> optionalCountryEntity = countryRepository.findByLocaleAndActiveTrue(locale);

        if (!optionalCountryEntity.isPresent()) {
            throw new ItemNotFoundException("Invalid locale -" + locale);
        }

        Country country = countryServiceMapper.mapEntityToDTO(optionalCountryEntity.get(), Country.class);
        List<Operator> operators = operatorService.getOperatorsListByCountryId(country.getId());

        map.setCountry(country);
        map.setOperators(operators);

        return map;
    }

    @Override
    public CountryOperatorsMap getCountryOperatorsMapByOperatorCode(String operatorCode) {
        CountryOperatorsMap map = new CountryOperatorsMap();
        Country country = operatorService.getCountryByOperatorCode(operatorCode);

        List<Operator> operators = operatorService.getOperatorsListByCountryId(country.getId());

        map.setCountry(country);
        map.setOperators(operators);

        return map;
    }
}
