package com.streaming.subscription.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.ConversionEntity;
import com.streaming.subscription.bean.jpa.SubscriptionEntity;

public interface ConversionRepository extends PagingAndSortingRepository<ConversionEntity, Integer> {
    List<SubscriptionEntity> findByMsisdnAndStatusInAndExpireAtAfter(String msisdn, String[] status, ZonedDateTime currentUTCTime);
}
