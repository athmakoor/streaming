package com.streaming.aggregator.timwe.bean.notication;

import java.util.List;

public class NotificationUserOptoutRequest {
    private Integer productId;
    private Integer pricepointId;
    private String mcc;
    private String mnc;
    private String msisdn;
    private String largeAccount;
    private String entryChannel;
    private String transactionUUID;
    private List<String> tags;
    private String trackingId;
    private String mnoDeliveryCode;
    private String totalCharged;

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

    public String getEntryChannel() {
        return entryChannel;
    }

    public void setEntryChannel(final String entryChannel) {
        this.entryChannel = entryChannel;
    }

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(final String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getMnoDeliveryCode() {
        return mnoDeliveryCode;
    }

    public void setMnoDeliveryCode(final String mnoDeliveryCode) {
        this.mnoDeliveryCode = mnoDeliveryCode;
    }

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(final String totalCharged) {
        this.totalCharged = totalCharged;
    }

    @Override
    public String toString() {
        return "NotificationUserOptoutRequest{" + "productId=" + productId + ", pricepointId=" + pricepointId
                + ", mcc='" + mcc + '\'' + ", mnc='" + mnc + '\'' + ", msisdn='" + msisdn + '\'' + ", largeAccount='"
                + largeAccount + '\'' + ", entryChannel='" + entryChannel + '\'' + ", transactionUUID='"
                + transactionUUID + '\'' + ", tags=" + tags + ", trackingId='" + trackingId + '\''
                + ", mnoDeliveryCode='" + mnoDeliveryCode + '\'' + ", totalCharged='" + totalCharged + '\'' + '}';
    }
}
