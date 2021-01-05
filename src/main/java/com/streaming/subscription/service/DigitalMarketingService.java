package com.streaming.subscription.service;

import java.io.UnsupportedEncodingException;

public interface DigitalMarketingService {
    void saveSubscription(String msisdn, String price, String currency, String provider, String partner) throws UnsupportedEncodingException;

    void saveSubscription(String msisdn, String price, String currency, String provider, String status, String partner) throws UnsupportedEncodingException;
}
