package com.streaming.aggregator.timwe.bean;

/**
 * Created by Hari on 2/3/2021.
 */
public class SubscriptionConsent {
    private String correlatorId;
    private String statusCode;

    public String getCorrelatorId() {
        return correlatorId;
    }

    public void setCorrelatorId(final String correlatorId) {
        this.correlatorId = correlatorId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final String statusCode) {
        this.statusCode = statusCode;
    }
}
