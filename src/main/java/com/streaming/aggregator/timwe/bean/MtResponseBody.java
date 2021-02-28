package com.streaming.aggregator.timwe.bean;

/**
 * Created by Hari on 1/22/2021.
 */
public class MtResponseBody {
    private String transactionUUID;

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(final String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    @Override
    public String toString() {
        return "MtResponseBody{" + "transactionUUID='" + transactionUUID + '\'' + '}';
    }
}
