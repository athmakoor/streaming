package com.streaming.bean;

import java.util.ArrayList;
import java.util.List;

public class CountryOperatorsMap {
    private Country country;
    private List<Operator> operators = new ArrayList<>();

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}
