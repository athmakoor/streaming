package com.streaming.aggregator.timwe.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Hari on 1/22/2021.
 */
public class MNODeliveryCodes {
    public static String DELIVERED = "DELIVERED";
    public static String NOT_DELIVERED = "NOT_DELIVERED";
    public static String NO_BALANCE = "NO_BALANCE";
    public static String UNKNOWN_MSISDN = "UNKNOWN_MSISDN";
    public static String BLACKLISTED = "BLACKLISTED";
    public static String REJECTED_BY_MNO = "REJECTED_BY_MNO";
    public static String EXPIRED_BY_MNO = "EXPIRED_BY_MNO";
    public static String MNO_SERVICE_UNAVAILABLE = "MNO_SERVICE_UNAVAILABLE";
    public static String SUCCESS = "SUCCESS";

    public static final Map<String, String> optOutMessages = ImmutableMap.<String , String>builder()
            .put("DELIVERED", "Successfull")
            .put("NOT_DELIVERED", "Message Sending Failed")
            .put("NO_BALANCE", "Unable to Charge because of no balance")
            .put("UNKNOWN_MSISDN","UnKnown Mobile number")
            .put("BLACKLISTED", "Your Mobile number blacklisted by MNO")
            .put("REJECTED_BY_MNO", "Service rejected by MNO")
            .put("EXPIRED_BY_MNO", "")
            .put("MNO_SERVICE_UNAVAILABLE", "MNO Service unavailable at this time. Try again later")
            .build();
}
