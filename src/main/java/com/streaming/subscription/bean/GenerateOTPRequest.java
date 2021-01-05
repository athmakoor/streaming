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
    private String partner = "";
    private String partnerTransactionId = "";

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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerTransactionId() {
        return partnerTransactionId;
    }

    public void setPartnerTransactionId(String partnerTransactionId) {
        this.partnerTransactionId = partnerTransactionId;
    }

    @Override
    public String toString() {
        return "GenerateOTPRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", clickId='" + clickId + '\'' +
                ", provider='" + provider + '\'' +
                ", partner='" + partner + '\'' +
                ", partnerTransactionId='" + partnerTransactionId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", packId='" + packId + '\'' +
                ", packPrice='" + packPrice + '\'' +
                ", packValidity='" + packValidity + '\'' +
                ", userIP='" + userIP + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
