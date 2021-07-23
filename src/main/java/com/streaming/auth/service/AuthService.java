package com.streaming.auth.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.streaming.auth.bean.AuthRequest;
import com.streaming.auth.bean.AuthResponse;

public interface AuthService {
    AuthResponse checkAndGenerateOTP(AuthRequest data, HttpServletRequest request) throws UnsupportedEncodingException;

    Boolean checkSubscription(String msisdn) throws UnsupportedEncodingException;

    Boolean verifyOTP(String msisdn, String otpText, HttpServletRequest request) throws UnsupportedEncodingException;

    AuthResponse regenerateOTP(String msisdn, HttpServletRequest request);

    public Boolean unSubscribe(String msisdn);
}
