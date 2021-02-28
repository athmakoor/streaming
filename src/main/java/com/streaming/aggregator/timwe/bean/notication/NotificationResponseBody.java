package com.streaming.aggregator.timwe.bean.notication;

/**
 * Created by Hari on 1/22/2021.
 */
public class NotificationResponseBody {
    private String transactionUUID;
    private String correlationId;

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(final String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "NotificationResponseBody{" + "transactionUUID='" + transactionUUID + '\'' + ", correlationId='"
                + correlationId + '\'' + '}';
    }
}
