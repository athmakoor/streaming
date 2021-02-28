package com.streaming.aggregator.timwe.bean;

/**
 * Created by Hari on 1/22/2021.
 */
public class MtRequest {
    private String productId;
    private String pricepointId;
    private String mcc;
    private String mnc;
    private String text;
    private String msisdn;
    private String largeAccount;
    private String sendDate;
    private String priority;
    private String timeZone;
    private String context;
    private String moTransactionUUID;

    public String getMcc() {
        return mcc;
    }

    public void setMcc(final String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(final String mnc) {
        this.mnc = mnc;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLargeAccount() {
        return largeAccount;
    }

    public void setLargeAccount(final String largeAccount) {
        this.largeAccount = largeAccount;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(final String sendDate) {
        this.sendDate = sendDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(final String timeZone) {
        this.timeZone = timeZone;
    }

    public String getContext() {
        return context;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public String getMoTransactionUUID() {
        return moTransactionUUID;
    }

    public void setMoTransactionUUID(final String moTransactionUUID) {
        this.moTransactionUUID = moTransactionUUID;
    }

    @Override
    public String toString() {
        return "MtRequest{" + "productId=" + productId + ", pricepointId=" + pricepointId + ", mcc='" + mcc + '\''
                + ", mnc='" + mnc + '\'' + ", text='" + text + '\'' + ", msisdn='" + msisdn + '\'' + ", largeAccount='"
                + largeAccount + '\'' + ", sendDate='" + sendDate + '\'' + ", priority='" + priority + '\''
                + ", timeZone='" + timeZone + '\'' + ", context='" + context + '\'' + ", moTransactionUUID='"
                + moTransactionUUID + '\'' + '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPricepointId() {
        return pricepointId;
    }

    public void setPricepointId(String pricepointId) {
        this.pricepointId = pricepointId;
    }
}
