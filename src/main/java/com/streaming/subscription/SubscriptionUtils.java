package com.streaming.subscription;

import com.google.gson.Gson;
import com.streaming.properties.PropertyManager;
import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.utils.request.Request;
import com.streaming.utils.request.RequestException;
import com.streaming.utils.request.RequestUtils;

public class SubscriptionUtils {
    public static GenerateOTPResponse generateOTP(GenerateOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPGenerateUrl();

        url = url.replace("{PUBLISHER_ID}", PropertyManager.getOTPPublisherId());
        url = url.replace("{CLICK_ID}", data.getClickId());
        url = url.replace("{PACK_ID}", data.getPackId());
        url = url.replace("{TENANT_ID}", PropertyManager.getOTPTenantId());
        url = url.replace("{MSISDN}", data.getMsisdn());
        url = url.replace("{TRANSACTION_ID}", "");
        url = url.replace("{REQUEST_TYPE}", "" + 1);

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            Gson gson = new Gson();
            return gson.fromJson(response, GenerateOTPResponse.class);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static GenerateOTPResponse regenerateOTP(GenerateOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPGenerateUrl();

        url = url.replace("{PUBLISHER_ID}", PropertyManager.getOTPPublisherId());
        url = url.replace("{CLICK_ID}", data.getClickId());
        url = url.replace("{PACK_ID}", data.getPackId());
        url = url.replace("{TENANT_ID}", PropertyManager.getOTPTenantId());
        url = url.replace("{MSISDN}", data.getMsisdn());
        url = url.replace("{TRANSACTION_ID}", data.getTransactionId());
        url = url.replace("{REQUEST_TYPE}", "" + 2);

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            Gson gson = new Gson();
            return gson.fromJson(response, GenerateOTPResponse.class);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static VerifyOTPResponse verifyOtp(VerifyOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPVerifyUrl();

        url = url.replace("{PUBLISHER_ID}", PropertyManager.getOTPPublisherId());
        url = url.replace("{TRANSACTION_ID}", data.getTransactionId());
        url = url.replace("{OTP_TEXT}", data.getOtpText());

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            Gson gson = new Gson();
            return gson.fromJson(response, VerifyOTPResponse.class);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }
}
