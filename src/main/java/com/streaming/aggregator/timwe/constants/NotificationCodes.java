package com.streaming.aggregator.timwe.constants;


import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Hari on 1/22/2021.
 */
public class NotificationCodes {

    public static final String OPTOUT_OK = "OPTOUT_CANCELED_OK";
    public static final String OPTOUT_ALREADY_CANCELLED = "OPTOUT_ALREADY_CANCELED";
    public static final String OPTOUT_ALL_OK = "OPTOUT_ALL_CANCELED_OK";
    public static final String OPTOUT_ALL_ALREADY_CANCELLED= "OPTOUT_ALL_ALREADY_CANCELED";
    public static final String OPTOUT_MISSING_DATA = "OPTOUT_MISSING_PARAM";
    public static final String OPTOUT_NO_SUB = "OPTOUT_NO_SUB";

    public static final Map<String, String> optOutMessages = ImmutableMap.<String , String>builder()
            .put("OPTOUT_CANCELED_OK", "")
            .put("OPTOUT_ALREADY_CANCELED", "Already Cancelled Unsub request")
            .put("OPTOUT_ALL_CANCELED_OK", "All UnSub cancelled")
            .put("OPTOUT_ALL_ALREADY_CANCELED","")
            .put("OPTOUT_MISSING_PARAM", "Data Missing to unsubscribe")
            .put("OPTOUT_NO_SUB", "No subscription found for given MobileNumber")
            .build();

}
