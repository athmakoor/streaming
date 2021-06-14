package com.streaming.controller;

import com.streaming.bean.Country;
import com.streaming.bean.CountryOperatorsMap;
import com.streaming.bean.Operator;
import com.streaming.service.CountryService;
import com.streaming.service.OperatorService;
import com.streaming.subscription.bean.SubscriptionPack;
import com.streaming.subscription.service.SubscriptionPackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins="*",allowedHeaders="*")
public class DataProviderController {
    @Resource
    private CountryService countryService;

    @Resource
    private OperatorService operatorService;

    @Resource
    private SubscriptionPackService subscriptionPackService;

    @GetMapping("/country/{locale}")
    public Country getCountryByLocale(@PathVariable("locale") final String locale) {
        return countryService.getCountryByLocale(locale);
    }

    @GetMapping("/country-provider-map/{locale}")
    public CountryOperatorsMap getCountryOperatorsMapByLocale(@PathVariable("locale") final String locale) {
        return countryService.getCountryOperatorsMapByLocale(locale);
    }

    @GetMapping("/country-operator-map/operatorCode/{operatorCode}")
    public CountryOperatorsMap getCountryOperatorsMapByOperatorCode(@PathVariable("operatorCode") final String operatorCode) {
        return countryService.getCountryOperatorsMapByOperatorCode(operatorCode);
    }

    @GetMapping("/countries")
    public List<Country> getCountriesList() {
        return countryService.getCountriesList();
    }

    @GetMapping("/all-operators")
    public List<Operator> getOperatorsList() {
        return operatorService.getOperatorsList();
    }

    @GetMapping("/operators/{country}")
    public List<Operator> findByCategories(@PathVariable("country") final Integer countryId) {
        return operatorService.getOperatorsListByCountryId(countryId);
    }

    @GetMapping("/subscription-packs/{provider}/{operatorCode}")
    public List<SubscriptionPack> findByProviderAndOperatorCode(@PathVariable("provider") final String provider, @PathVariable("operatorCode") final String operatorCode) {
        return subscriptionPackService.findByProviderAndOperatorCode(provider, operatorCode);
    }
}
