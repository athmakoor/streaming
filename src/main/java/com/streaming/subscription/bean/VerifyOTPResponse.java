package com.streaming.subscription.bean;

public class VerifyOTPResponse {
    private String errCode;
    private String errMsg;
    private String interfaceId;
    private String transactionId;

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

    @Override
    public String toString() {
        return "VerifyOTPResponse{" +
                "errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
