package com.streaming.aggregator.timwe.bean.notication;


public class NotificationMoRequest {
    private Integer productId;
    private Integer pricepointId;
    private String mcc;
    private String mnc;
    private String text;
    private String msisdn;
    private String largeAccount;
    private String transactionUUID;
    private String tags;
    private String trackingId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public Integer getPricepointId() {
        return pricepointId;
    }

    public void setPricepointId(final Integer pricepointId) {
        this.pricepointId = pricepointId;
    }

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

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(final String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "NotificationMoRequest{" + "productId=" + productId + ", pricepointId=" + pricepointId + ", mcc='" + mcc
                + '\'' + ", mnc='" + mnc + '\'' + ", text='" + text + '\'' + ", msisdn='" + msisdn + '\''
                + ", largeAccount='" + largeAccount + '\'' + ", transactionUUID='" + transactionUUID + '\'' + ", tags='"
                + tags + '\'' + '}';
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
}

