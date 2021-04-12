package com.streaming.repository;

import com.streaming.bean.jpa.CountryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, Integer> {
    List<CountryEntity> findByActiveTrue();

    Optional<CountryEntity> findByLocaleAndActiveTrue(String locale);
}
