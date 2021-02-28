package com.streaming.aggregator.timwe.service;

import com.streaming.aggregator.timwe.bean.CgUrlRequest;
import com.streaming.aggregator.timwe.bean.ServiceResponse;
import com.streaming.aggregator.timwe.bean.SubscriptionStatus;
import com.streaming.subscription.bean.Subscription;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by HARIKRISHNA on 1/28/2021.
 */
public interface TimWeSubscriptionService {
    ServiceResponse getCGSource(CgUrlRequest cgUrlRequest, HttpServletRequest request);

    ServiceResponse unSubscribe(String msisdn, HttpServletRequest request);

    ServiceResponse updateStatus(String correlatedId, String statusCode);

    ServiceResponse checkSub(String msisdn);

    SubscriptionStatus getSubscriptionStatus(String msisdn);

    Subscription save(String status, String clickId, String msisdn);

    Subscription save(String status, String clickId, String msisdn, String totalCharged, String packId);

    Subscription save(String status, String clickId, String msisdn, String packId, Boolean isRenew);
}