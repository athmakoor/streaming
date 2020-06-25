package com.streaming.subscription.bean;

import javax.validation.constraints.NotNull;

public class GenerateOTPRequest {
    @NotNull
    private String msisdn;
    @NotNull
    private String clickId;
    private String transactionId;
    @NotNull
    private String packId;

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
}
