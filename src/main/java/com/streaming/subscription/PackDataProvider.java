package com.streaming.subscription;

import com.streaming.constant.Provider;
import com.streaming.subscription.bean.GenerateOTPRequest;

public class PackDataProvider {
    public static GenerateOTPRequest getDefaultRequestByProvider(String provider) {
        GenerateOTPRequest request = new GenerateOTPRequest();
        request.setProvider(provider);
        if (Provider.ZAIN_KUWAIT.equals(provider)) {
            request.setPackPrice("600");
            request.setPackValidity("7");
        }

        return request;
    }
}
