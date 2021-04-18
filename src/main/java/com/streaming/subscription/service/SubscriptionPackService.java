package com.streaming.subscription.service;

import com.streaming.subscription.bean.SubscriptionPack;

import java.util.List;

public interface SubscriptionPackService {
    SubscriptionPack findByProviderAndPackId(String provider, String packId);

    List<SubscriptionPack> findByProviderAndOperatorCode(String provider, String operatorCode);

    SubscriptionPack findByName(String provider, String planName);
}
