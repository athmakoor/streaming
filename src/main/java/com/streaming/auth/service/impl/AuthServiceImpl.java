package com.streaming.auth.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.streaming.auth.bean.AuthRequest;
import com.streaming.auth.bean.AuthResponse;
import com.streaming.auth.bean.jpa.AuthRequestEntity;
import com.streaming.auth.repository.AuthRequestRepository;
import com.streaming.auth.service.AuthService;
import com.streaming.constant.Provider;
import com.streaming.properties.PropertyManager;
import com.streaming.subscription.PackDataProvider;
import com.streaming.subscription.SubscriptionUtils;
import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.SubscriptionService;
import com.streaming.utils.IpUtil;
import com.streaming.utils.request.Request;
import com.streaming.utils.request.RequestException;
import com.streaming.utils.request.RequestUtils;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private AuthRequestRepository authRequestRepository;

    private static final String SUBSCRIPTION_CHECK_URL = "http://dm.vkandigital.com/api/checkSubscription?msisdn={{MSISDN}}";
    private static final String NEW_SUBSCRIPTION_URL = "http://dm.vkandigital.com/api/subscription/internal-subscribe?msisdn={{MSISDN}}&cur={{CURRENCY}}&price={{PRICE}}";
    @Override
    public AuthResponse checkAndGenerateOTP(AuthRequest data, HttpServletRequest request) {
        AuthResponse authResponse = new AuthResponse();

       /* Boolean activeSubscription = checkSubscription(data.getMsisdn());

        if (activeSubscription) {
            authResponse.setAuthenticated(true);
        } else {
            GenerateOTPRequest generateOTPRequest = PackDataProvider.getDefaultRequestByProvider(data.getProvider());
            String id = "" + new Date().getTime();

            generateOTPRequest.setMsisdn(data.getMsisdn());
            generateOTPRequest.setClickId(id);
            generateOTPRequest.setTransactionId(id);
            generateOTPRequest.setUserIP(IpUtil.getClientIpAddr(request));
            GenerateOTPResponse generateOTPResponse = subscriptionService.generateOtp(generateOTPRequest);

            AuthRequestEntity authRequestEntity = new AuthRequestEntity();

            authRequestEntity.setMsisdn(data.getMsisdn());
            authRequestEntity.setClickId(id);
            authRequestEntity.setProvider(data.getProvider());
            authRequestEntity.setUserIp(IpUtil.getClientIpAddr(request));

            authRequestRepository.save(authRequestEntity);

            if (generateOTPResponse.getErrCode().equals("0")) {
                authResponse.setOtpSent(true);
            } else {
                throw new RequestException(generateOTPResponse.getErrMsg());
            }
        }*/

        authResponse.setOtpSent(true);
        return authResponse;
    }

    @Override
    public Boolean checkSubscription(String msisdn) {
        String url = SUBSCRIPTION_CHECK_URL.replace("{{MSISDN}}", msisdn);

        Request request = new Request();

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            return Boolean.parseBoolean(response);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    @Override
    public Boolean verifyOTP(String msisdn, String otpText) {
        /*VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();

        Optional<AuthRequestEntity> authRequestEntityOptional = authRequestRepository.findFirstByMsisdnOrderByIdDesc(msisdn);

        if (authRequestEntityOptional.isPresent()) {
            AuthRequestEntity entity = authRequestEntityOptional.get();
            verifyOTPRequest.setOtpText(otpText);
            verifyOTPRequest.setTransactionId(entity.getClickId());
            verifyOTPRequest.setProvider(entity.getProvider());
            verifyOTPRequest.setPackPrice("2000");
            verifyOTPRequest.setPackValidity("30");

            VerifyOTPResponse verifyOTPResponse = subscriptionService.verifyOtp(verifyOTPRequest);

            if (verifyOTPResponse.getErrCode().equals("0")) {
                String url = NEW_SUBSCRIPTION_URL.replace("{{MSISDN}}", msisdn);

                url = url.replace("{{CURRENCY}}", "AED");
                url = url.replace("{{PRICE}}", "2000");

                Request request = new Request();

                try {
                    request.setMethod("GET");
                    request.setPath(url);
                    String response = RequestUtils.getResponse(request, null);

                    System.out.println(response);
                } catch (RequestException e) {
                    e.printStackTrace();
                    throw new RequestException(e.getMessage());
                }

                return true;
            } else {
                throw new RequestException(verifyOTPResponse.getErrMsg());
            }
        }

        return false;*/
        String url = NEW_SUBSCRIPTION_URL.replace("{{MSISDN}}", msisdn);

        url = url.replace("{{CURRENCY}}", "AED");
        url = url.replace("{{PRICE}}", "2000");

        Request request = new Request();

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            System.out.println(response);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }

        return true;
    }

    @Override
    public AuthResponse regenerateOTP(String msisdn) {
        Optional<AuthRequestEntity> authRequestEntityOptional = authRequestRepository.findFirstByMsisdnOrderByIdDesc(msisdn);
        AuthResponse authResponse = new AuthResponse();

        if (authRequestEntityOptional.isPresent()) {
            AuthRequestEntity entity = authRequestEntityOptional.get();

            GenerateOTPRequest generateOTPRequest = PackDataProvider.getDefaultRequestByProvider(entity.getProvider());

            generateOTPRequest.setMsisdn(msisdn);
            generateOTPRequest.setClickId(entity.getClickId());
            generateOTPRequest.setTransactionId(entity.getClickId());
            generateOTPRequest.setUserIP(entity.getUserIp());

            GenerateOTPResponse generateOTPResponse = subscriptionService.regenerateOTP(generateOTPRequest);

            if (generateOTPResponse.getErrCode().equals("0")) {
                authResponse.setOtpSent(true);
            }
        }

        return authResponse;
    }
}
