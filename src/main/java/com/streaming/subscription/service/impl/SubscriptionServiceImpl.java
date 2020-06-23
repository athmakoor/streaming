package com.streaming.subscription.service.impl;

import org.springframework.stereotype.Service;

import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public GenerateOTPResponse generateOtp(final GenerateOTPRequest data) {
        return null;
    }

    @Override
    public VerifyOTPResponse verifyOtp(final VerifyOTPRequest data) {
        return null;
    }

    @Override
    public GenerateOTPResponse regenerateOTP(GenerateOTPRequest data) {
        return null;
    }
}
