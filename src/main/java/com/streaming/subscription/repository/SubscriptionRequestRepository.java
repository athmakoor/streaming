package com.streaming.subscription.repository;

import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRequestRepository extends PagingAndSortingRepository<SubscriptionRequestEntity, Integer> {
    List<SubscriptionRequestEntity> findByClickId(String clickId);

    List<SubscriptionRequestEntity> findByCreatedAtAfter(ZonedDateTime date);

    Optional<SubscriptionRequestEntity> findFirstByMsisdnOrderByIdDesc(String msisdn);

    Optional<SubscriptionRequestEntity> findFirstBySubscriptionContractIdOrderByIdDesc(String contractId);
}
