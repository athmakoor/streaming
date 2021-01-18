package com.streaming.subscription.service;

import java.io.UnsupportedEncodingException;

public interface DigitalMarketingService {
    void saveSubscription(String msisdn, String price, String currency, String provider, String partner, String partnerTransactionId )throws UnsupportedEncodingException;

    void saveSubscription(String msisdn, String price, String currency, String provider, String partner, String partnerTransactionId, String status) throws UnsupportedEncodingException;

    void sendPartnerNotification(String partnerTransactionId);
}
