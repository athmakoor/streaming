package com.streaming.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.streaming.service.SubscriptionService;
import com.streaming.utils.SubscriptionUtil;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public String subscribe(String msisdn) throws IOException {
        String status = SubscriptionUtil.subscribe(msisdn);

        return status;
    }
}
