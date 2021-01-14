package com.streaming.subscription.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.NotificationsService;
import com.streaming.subscription.service.SubscriptionService;


@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {
    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private NotificationsService notificationsService;

    @Value("web.url")
    private String webUrl;

    @GetMapping("/zain-kuwait/msisdn")
    public void getZainKwuaitMsisdn(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Msisdn Response: " + request.getQueryString());
        notificationsService.save("msisdn", request);

        String url = webUrl + "za-kw?" + request.getQueryString();
        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/zain-kuwait/notification")
    public void getNotification(final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Notification Response: " + request.getQueryString());
        notificationsService.save("notification", request);
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
    public VerifyOTPResponse verifyOTP(@Valid @RequestBody final VerifyOTPRequest data) throws IOException {
        return subscriptionService.verifyOtp(data);
    }

    @GetMapping("/create")
    public void creaqte(final HttpServletRequest request) {
        notificationsService.create();
    }
}
