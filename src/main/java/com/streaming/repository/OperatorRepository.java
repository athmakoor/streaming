package com.streaming.repository;

import com.streaming.bean.jpa.OperatorEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperatorRepository extends PagingAndSortingRepository<OperatorEntity, Integer> {
    List<OperatorEntity> findByActiveTrue();

    List<OperatorEntity> findByCountry_IdAndActiveTrue(Integer countryId);

    OperatorEntity findByOperatorCode(String operatorCode);

}
