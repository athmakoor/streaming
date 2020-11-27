package com.streaming.subscription.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.SubscriptionEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface SubscriptionRepository extends PagingAndSortingRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findByCreatedAtAfter(ZonedDateTime date);
}
