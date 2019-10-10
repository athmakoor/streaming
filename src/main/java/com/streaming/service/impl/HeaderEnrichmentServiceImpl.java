package com.streaming.service.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.streaming.service.HeaderEnrichmentService;
import com.streaming.utils.HeaderEnrichmentUtil;

@Component
public class HeaderEnrichmentServiceImpl implements HeaderEnrichmentService {
    private static final String WIFI_ERROR = "ON WIFI";
    private static final String MISSING_DATE_ERROR = "ROLE ID MISSING";
    private static final String NOT_LIVE_ERROR = "ROLE ID IS NOT LIVE. CONTACT ADMIN";
    private static final String GENERIC_ERROR = "Something went wrong";
    private static final String DECRYPT_ERROR = "Invalid Msisdn";

    @Override
    public void updateModel(Map<String, Object> model, String correlatorId, String token, Integer statusCode) {
        if (statusCode == 1) {
            if (token != null) {
                String msisdn = HeaderEnrichmentUtil.decryptMsisdn(token);

                if (msisdn != null) {
                    model.put("msisdn", msisdn);
                } else{
                    model.put("error", WIFI_ERROR);
                }
            } else {
                model.put("error", WIFI_ERROR);
            }
        } else if (statusCode == -2) {
            model.put("error", WIFI_ERROR);
        } else if (statusCode == 0){
            model.put("error", MISSING_DATE_ERROR);
        } else if (statusCode == -14) {
            model.put("error", NOT_LIVE_ERROR);
        } else {
            model.put("error", GENERIC_ERROR);
        }
    }
}
