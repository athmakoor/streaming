package com.streaming.bean;

public class AppApiReturn {
    private String response;
    private String errorMessage;
//    private String msisdn;
//    private String transactionId;

    public AppApiReturn(String response, String errorMessage) {
        this.response = response;
        this.errorMessage = errorMessage;
//        this.msisdn = msisdn;
//        this.transactionId = transactionId;
    }

//    public String getMsisdn() {
//        return msisdn;
//    }
//
//    public void setMsisdn(String msisdn) {
//        this.msisdn = msisdn;
//    }
//
//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(String transactionId) {
//        this.transactionId = transactionId;
//    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AppApiReturn{" +
                "response='" + response + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
