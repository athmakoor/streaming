package com.streaming.service;

import com.streaming.bean.Country;
import com.streaming.bean.Operator;

import java.util.List;

public interface OperatorService {
    List<Operator> getOperatorsList();

    List<Operator> getOperatorsListByCountryId(Integer countryId);

    Country getCountryByOperatorCode(String code);

}
