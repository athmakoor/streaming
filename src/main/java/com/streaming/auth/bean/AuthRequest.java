package com.streaming.auth.bean;

public class AuthRequest {
    private String provider = "";
    private String msisdn = "";
    private String partner = "";

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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
