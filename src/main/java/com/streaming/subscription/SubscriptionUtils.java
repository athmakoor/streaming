package com.streaming.subscription;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.streaming.properties.PropertyManager;
import com.streaming.subscription.bean.CgResponse;
import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.utils.XMLToJSONConverter;
import com.streaming.utils.request.Request;
import com.streaming.utils.request.RequestException;
import com.streaming.utils.request.RequestUtils;

public class SubscriptionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionUtils.class);

    public static GenerateOTPResponse generateOTP(GenerateOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPGenerateUrl(data.getProvider());

        url = url.replace("{CLICK_ID}", data.getClickId());
        url = url.replace("{PACK_ID}", data.getPackId());
        url = url.replace("{MSISDN}", data.getMsisdn());
        url = url.replace("{PACK_PRICE}", data.getPackPrice());
        url = url.replace("{PACK_VALIDITY}", data.getPackValidity());
        url = url.replace("{TRANSACTION_ID}", "");
        url = url.replace("{REQUEST_TYPE}", "" + 1);

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);
            LOGGER.debug(response);

            if (data.getProvider().equals("zain")) {
                return SubscriptionUtils.convertGenerateOTPXMLToJSON(response);
            }

            Gson gson = new Gson();
            return gson.fromJson(response, GenerateOTPResponse.class);
        } catch (RequestException | IOException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static GenerateOTPResponse regenerateOTP(GenerateOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPGenerateUrl(data.getProvider());

        url = url.replace("{CLICK_ID}", data.getClickId());
        url = url.replace("{PACK_ID}", data.getPackId());
        url = url.replace("{MSISDN}", data.getMsisdn());
        url = url.replace("{TRANSACTION_ID}", data.getTransactionId());
        url = url.replace("{PACK_PRICE}", data.getPackPrice());
        url = url.replace("{PACK_VALIDITY}", data.getPackValidity());
        url = url.replace("{REQUEST_TYPE}", "" + 2);

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            LOGGER.debug(response);

            if (data.getProvider().equals("zain")) {
                return SubscriptionUtils.convertGenerateOTPXMLToJSON(response);
            }

            Gson gson = new Gson();
            return gson.fromJson(response, GenerateOTPResponse.class);
        } catch (RequestException | IOException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static VerifyOTPResponse verifyOtp(VerifyOTPRequest data) {
        Request request = new Request();
        String url = PropertyManager.getOTPVerifyUrl(data.getProvider());

        url = url.replace("{TRANSACTION_ID}", data.getTransactionId());
        url = url.replace("{OTP_TEXT}", data.getOtpText());
        url = url.replace("{PACK_PRICE}", data.getPackPrice());
        url = url.replace("{PACK_VALIDITY}", data.getPackValidity());

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);
            LOGGER.debug(response);

            Gson gson = new Gson();

            if (data.getProvider().equals("zain")) {
                return SubscriptionUtils.convertVerifyOTPXMLToJSON(response);
            }

            return gson.fromJson(response, VerifyOTPResponse.class);
        } catch (RequestException | IOException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static GenerateOTPResponse convertGenerateOTPXMLToJSON(String xml) throws IOException {
        XMLToJSONConverter<CgResponse> converter = new XMLToJSONConverter<>();
        xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"UTF8\"?>", "");
        xml = xml.replaceAll("<cgResponse>", "<CgResponse>");
        xml = xml.replaceAll("</cgResponse>", "</CgResponse>");
        CgResponse response = converter.convert(xml, CgResponse.class);

        GenerateOTPResponse otpResponse = new GenerateOTPResponse();
        otpResponse.setErrCode(response.getError_code());
        otpResponse.setErrMsg(response.getErrorDesc());
        otpResponse.setTransactionId(response.getCgId());

        return otpResponse;
    }

    public static VerifyOTPResponse convertVerifyOTPXMLToJSON(String xml) throws IOException {
        XMLToJSONConverter<CgResponse> converter = new XMLToJSONConverter<>();
        xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"UTF8\"?>", "");
        xml = xml.replaceAll("<cgResponse>", "<CgResponse>");
        xml = xml.replaceAll("</cgResponse>", "</CgResponse>");
        CgResponse response = converter.convert(xml, CgResponse.class);

        VerifyOTPResponse otpResponse = new VerifyOTPResponse();
        otpResponse.setErrCode(response.getError_code());
        otpResponse.setErrMsg(response.getErrorDesc());
        otpResponse.setTransactionId(response.getCgId());

        return otpResponse;
    }
}
