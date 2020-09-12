package com.streaming.subscription.service;

import java.io.UnsupportedEncodingException;

public interface DigitalMarketingService {
    void saveSubscription(String msisdn, String price, String currency) throws UnsupportedEncodingException;

    void saveSubscription(String msisdn, String price, String currency, String status) throws UnsupportedEncodingException;
}
