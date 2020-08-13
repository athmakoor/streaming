package com.streaming.subscription.bean;

import javax.validation.constraints.NotNull;

public class GenerateOTPRequest {
    @NotNull
    private String msisdn;
    @NotNull
    private String clickId;
    @NotNull
    private String provider = "";
    private String transactionId = "";
    private String packId = "";
    private String packPrice = "";
    private String packValidity = "";
    private String userIP = "";
    private String sessionId = "";

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(String packPrice) {
        this.packPrice = packPrice;
    }

    public String getPackValidity() {
        return packValidity;
    }

    public void setPackValidity(String packValidity) {
        this.packValidity = packValidity;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
