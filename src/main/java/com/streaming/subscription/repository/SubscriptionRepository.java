package com.streaming.subscription.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.SubscriptionEntity;

public interface SubscriptionRepository extends PagingAndSortingRepository<SubscriptionEntity, Integer> {
}
