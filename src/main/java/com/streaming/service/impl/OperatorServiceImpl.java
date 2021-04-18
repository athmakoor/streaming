package com.streaming.service.impl;

import com.streaming.bean.Country;
import com.streaming.bean.Operator;
import com.streaming.bean.jpa.CountryEntity;
import com.streaming.bean.jpa.OperatorEntity;
import com.streaming.repository.CountryRepository;
import com.streaming.repository.OperatorRepository;
import com.streaming.service.OperatorService;
import com.streaming.service.mapping.ServiceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class OperatorServiceImpl implements OperatorService {
    @Resource
    private ServiceMapper<Operator, OperatorEntity> operatorServiceMapper;
    @Resource
    private ServiceMapper<Country, CountryEntity> countryServiceMapper;

    @Resource
    private CountryRepository countryRepository;

    @Resource
    private OperatorRepository operatorRepository;

    @Override
    public List<Operator> getOperatorsList() {
        List<Operator> operators = new ArrayList<>();
        List<OperatorEntity> providerEntities = operatorRepository.findByActiveTrue();

        for (OperatorEntity entity : providerEntities) {
            Operator operator = operatorServiceMapper.mapEntityToDTO(entity, Operator.class);
            operator.setCountry(countryServiceMapper.mapEntityToDTO(entity.getCountry(), Country.class));

            operators.add(operator);
        }

        return operators;
    }

    @Override
    public List<Operator> getOperatorsListByCountryId(Integer countryId) {
        List<Operator> operators = new ArrayList<>();
        List<OperatorEntity> providerEntities = operatorRepository.findByCountry_IdAndActiveTrue(countryId);

        for (OperatorEntity entity : providerEntities) {
            Operator operator = operatorServiceMapper.mapEntityToDTO(entity, Operator.class);
            operator.setCountry(countryServiceMapper.mapEntityToDTO(entity.getCountry(), Country.class));

            operators.add(operator);
        }

        return operators;
    }

    @Override
    public Country getCountryByOperatorCode(String code) {
        OperatorEntity operatorEntity = operatorRepository.findByOperatorCode(code);

        Country country =  countryServiceMapper.mapEntityToDTO(operatorEntity.getCountry(), Country.class);

        return country;
    }
}
