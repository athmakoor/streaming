package com.streaming.subscription.bean;

public class GenerateOTPResponse {
    private String errCode;
    private String errMsg;
    private String interfaceId;
    private String transactionId;
    private String msisdn;
    private String pack;
    private String r;
    private String t;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "{" +
                "errCode:" + errCode +
                ", errMsg:" + errMsg +
                ", interfaceId:" + interfaceId +
                ", transactionId:" + transactionId +
                ", msisdn:" + msisdn +
                ", pack:" + pack +
                ", r:" + r +
                ", t:" + t +
                '}';
    }
}
