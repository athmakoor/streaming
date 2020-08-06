package com.streaming.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.auth.bean.jpa.AuthRequestEntity;

public interface AuthRequestRepository extends PagingAndSortingRepository<AuthRequestEntity, Integer> {
    Optional<AuthRequestEntity> findLatestByMsisdn(String msisdn);
}
