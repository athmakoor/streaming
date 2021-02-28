package com.streaming.aggregator.timwe.bean;

/**
 * Created by Hari on 1/27/2021.
 */
public class SubscriptionResponseBody {
    private String transactionId;
    private String subscriptionResult;
    private String subscriptionError;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSubscriptionResult() {
        return subscriptionResult;
    }

    public void setSubscriptionResult(final String subscriptionResult) {
        this.subscriptionResult = subscriptionResult;
    }

    public String getSubscriptionError() {
        return subscriptionError;
    }

    public void setSubscriptionError(final String subscriptionError) {
        this.subscriptionError = subscriptionError;
    }

    @Override
    public String toString() {
        return "SubscriptionResponseBody{" + "transactionId='" + transactionId + '\'' + ", subscriptionResult='"
                + subscriptionResult + '\'' + ", subscriptionError='" + subscriptionError + '\'' + '}';
    }
}
