package com.streaming.subscription.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;

public interface SubscriptionRequestRepository extends PagingAndSortingRepository<SubscriptionRequestEntity, Integer> {
}
