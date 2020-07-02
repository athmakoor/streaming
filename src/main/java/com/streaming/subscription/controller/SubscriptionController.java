package com.streaming.subscription.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.SubscriptionService;


@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {
    @Resource
    private SubscriptionService subscriptionService;

    @GetMapping("/zain-kuwait/msisdn")
    public void getZainKwuaitMsisdn(final HttpServletRequest request) {
        System.out.println(request.getQueryString());
    }

    @GetMapping("/zain-kuwait/notification")
    public void getNotification(final HttpServletRequest request) {
        System.out.println(request.getQueryString());
    }

    @PostMapping("/generateOTP")
    public GenerateOTPResponse generateOtp(@Valid @RequestBody final GenerateOTPRequest data) throws IOException {
        return subscriptionService.generateOtp(data);
    }

    @PostMapping("/regenerateOTP")
    public GenerateOTPResponse regenerateOTP(@Valid @RequestBody final GenerateOTPRequest data) throws IOException {
        return subscriptionService.regenerateOTP(data);
    }

    @PostMapping("/verifyOTP")
    public VerifyOTPResponse regenerateOTP(@Valid @RequestBody final VerifyOTPRequest data) throws IOException {
        return subscriptionService.verifyOtp(data);
    }
}
