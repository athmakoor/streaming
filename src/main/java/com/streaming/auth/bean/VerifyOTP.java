package com.streaming.auth.bean;

public class VerifyOTP {
    private String msisdn;
    private String otpText;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getOtpText() {
        return otpText;
    }

    public void setOtpText(String otpText) {
        this.otpText = otpText;
    }
}
