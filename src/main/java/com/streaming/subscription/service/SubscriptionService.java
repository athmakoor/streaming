package com.streaming.subscription.service;

import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;

public interface SubscriptionService {
    GenerateOTPResponse generateOtp(GenerateOTPRequest data);

    VerifyOTPResponse verifyOtp(VerifyOTPRequest data);

    GenerateOTPResponse regenerateOTP(GenerateOTPRequest data);
}
