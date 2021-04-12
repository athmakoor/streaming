package com.streaming.subscription.repository;

import com.streaming.subscription.bean.jpa.SubscriptionPackEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionPackRepository extends PagingAndSortingRepository<SubscriptionPackEntity, Integer> {
    Optional<SubscriptionPackEntity> findByProviderAndSku(String provider, String packId);

    List<SubscriptionPackEntity> findByProviderAndOperatorCode(String provider, String operatorCode);

    Optional<SubscriptionPackEntity> findByProviderAndName(String provider, String planName);

}
