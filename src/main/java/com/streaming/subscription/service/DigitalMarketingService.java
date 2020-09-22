package com.streaming.subscription.service;

import java.io.UnsupportedEncodingException;

public interface DigitalMarketingService {
    void saveSubscription(String msisdn, String price, String currency, String provider) throws UnsupportedEncodingException;

    void saveSubscription(String msisdn, String price, String currency, String provider, String status) throws UnsupportedEncodingException;
}
