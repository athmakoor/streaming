package com.streaming.subscription.bean;

import javax.validation.constraints.NotNull;

public class VerifyOTPRequest {
    private String transactionId;
    private String provider = "";
    @NotNull
    private String otpText;
    private String packPrice = "";
    private String packValidity = "";
    @NotNull
    private String msisdn = "";
    private String userIP = "";
    private String sessionId = "";
    private String partner = "";

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOtpText() {
        return otpText;
    }

    public void setOtpText(String otpText) {
        this.otpText = otpText;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
