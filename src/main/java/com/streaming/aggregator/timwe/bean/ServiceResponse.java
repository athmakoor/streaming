package com.streaming.aggregator.timwe.bean;

import java.io.Serializable;

/**
 * Created by Hari on 1/28/2021.
 */
public class ServiceResponse implements Serializable{
    private String data;
    private Integer serviceCode;
    private Boolean hasError;
    private String  errorMessage;
    private String message;
    private String transactionId;

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(final Boolean hasError) {
        this.hasError = hasError;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }
}
