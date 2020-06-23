package com.streaming.subscription.bean;

public class VerifyOTPRequest {
    private String transactionId;
    private String otpText;

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
}
