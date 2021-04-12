package com.streaming.subscription.bean;

import com.streaming.UTCDateTimeConverter;

import javax.persistence.Convert;
import java.time.ZonedDateTime;

public class SubscriptionRequest {
    private Integer id;

    private String msisdn;

    private String responseMessage;

    private String requestBody;

    private Boolean regenerate;

    private String clickId;

    private String transactionId;

    private String subscriptionContractId;

    private String price;

    private String validity;

    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Boolean getRegenerate() {
        return regenerate;
    }

    public void setRegenerate(Boolean regenerate) {
        this.regenerate = regenerate;
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

    public String getSubscriptionContractId() {
        return subscriptionContractId;
    }

    public void setSubscriptionContractId(String subscriptionContractId) {
        this.subscriptionContractId = subscriptionContractId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
