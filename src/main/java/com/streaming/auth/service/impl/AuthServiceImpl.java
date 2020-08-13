package com.streaming.auth.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.streaming.auth.bean.AuthRequest;
import com.streaming.auth.bean.AuthResponse;
import com.streaming.auth.bean.jpa.AuthRequestEntity;
import com.streaming.auth.repository.AuthRequestRepository;
import com.streaming.auth.service.AuthService;
import com.streaming.subscription.PackDataProvider;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String SUBSCRIPTION_CHECK_URL = "http://dm.vkandigital.com/api/checkSubscription?msisdn={{MSISDN}}";
    private static final String NEW_SUBSCRIPTION_URL = "http://dm.vkandigital.com/api/subscription/internal-subscribe?msisdn={{MSISDN}}&cur={{CURRENCY}}&price={{PRICE}}";
    @Override
    public AuthResponse checkAndGenerateOTP(AuthRequest data, HttpServletRequest request) throws UnsupportedEncodingException {
        AuthResponse authResponse = new AuthResponse();

        Boolean activeSubscription = checkSubscription(data.getMsisdn());

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
        }

        return authResponse;
    }

    @Override
    public Boolean checkSubscription(String msisdn) throws UnsupportedEncodingException {
        String url = SUBSCRIPTION_CHECK_URL.replace("{{MSISDN}}", URLEncoder.encode(msisdn, "UTF-8"));

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
    public Boolean verifyOTP(String msisdn, String otpText, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();

        Optional<AuthRequestEntity> authRequestEntityOptional = authRequestRepository.findFirstByMsisdnOrderByIdDesc(msisdn);
        LOGGER.debug("Verify otp called:" + msisdn + " " + otpText);

        if (authRequestEntityOptional.isPresent()) {
            AuthRequestEntity entity = authRequestEntityOptional.get();
            verifyOTPRequest.setOtpText(otpText);
            verifyOTPRequest.setTransactionId(entity.getClickId());
            verifyOTPRequest.setProvider(entity.getProvider());
            verifyOTPRequest.setPackPrice("2000");
            verifyOTPRequest.setPackValidity("30");
            verifyOTPRequest.setMsisdn(msisdn);
            verifyOTPRequest.setUserIP(IpUtil.getClientIpAddr(httpServletRequest));

            VerifyOTPResponse verifyOTPResponse = subscriptionService.verifyOtp(verifyOTPRequest);

            if (verifyOTPResponse.getErrCode().equals("0")) {
                LOGGER.debug("Verify otp success:" + msisdn + " " + otpText);
                String url = NEW_SUBSCRIPTION_URL.replace("{{MSISDN}}", URLEncoder.encode(msisdn, "UTF-8"));

                url = url.replace("{{CURRENCY}}", "AED");
                url = url.replace("{{PRICE}}", "2000");

                Request request = new Request();

                try {
                    request.setMethod("GET");
                    request.setPath(url);
                    String response = RequestUtils.getResponse(request, null);

                    LOGGER.debug("Saved Subscription "  + msisdn + " " + otpText);
                    LOGGER.debug(response);
                } catch (RequestException e) {
                    e.printStackTrace();
                    throw new RequestException(e.getMessage());
                }

                return true;
            } else {
                throw new RequestException(verifyOTPResponse.getErrMsg());
            }
        }

        return false;
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
