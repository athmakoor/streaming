package com.streaming.subscription.bean;

import javax.validation.constraints.NotNull;

public class VerifyOTPRequest {
    private String transactionId;
    private String provider = "";
    private String otpText;
    private String packPrice = "";
    private String packValidity = "";
    @NotNull
    private String msisdn = "";
    private String userIP = "";

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
}
