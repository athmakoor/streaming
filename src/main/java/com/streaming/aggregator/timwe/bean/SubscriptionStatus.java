package com.streaming.aggregator.timwe.bean;

/**
 * Created by HARIKRISHNA on 2/23/2021.
 */
public class SubscriptionStatus {
    private String statusCode;
    private String status;
    private String correlatedId;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public String getCorrelatedId() {
        return correlatedId;
    }

    public void setCorrelatedId(String correlatedId) {
        this.correlatedId = correlatedId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
