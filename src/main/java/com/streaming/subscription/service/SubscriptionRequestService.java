package com.streaming.subscription.service;

import com.streaming.subscription.bean.SubscriptionRequest;

public interface SubscriptionRequestService {
    SubscriptionRequest findLatestByMsisdn(String msisdn);

    SubscriptionRequest findByMsisdnAndCorrelatedId(String msisdn, String correlatedId);

    SubscriptionRequest findLatestBySubscriptionContractId(String contractId);
}
