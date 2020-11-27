package com.streaming.subscription.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;

public interface SubscriptionRequestRepository extends PagingAndSortingRepository<SubscriptionRequestEntity, Integer> {
    List<SubscriptionRequestEntity> findByClickId(String clickId);

    List<SubscriptionRequestEntity> findByCreatedAtAfter(ZonedDateTime date);
}
