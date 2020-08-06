package com.streaming.auth.service;

import javax.servlet.http.HttpServletRequest;

import com.streaming.auth.bean.AuthRequest;
import com.streaming.auth.bean.AuthResponse;

public interface AuthService {
    AuthResponse checkAndGenerateOTP(AuthRequest data, HttpServletRequest request);

    Boolean checkSubscription(String msisdn);

    Boolean verifyOTP(String msisdn, String otpText);

    AuthResponse regenerateOTP(String msisdn);
}
