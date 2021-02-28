package com.streaming.aggregator.timwe.bean.notication;

import java.util.List;

/**
 * Created by Hari on 1/22/2021.
 */
public class NotificationDNRequest {
    private Integer productId;
    private Integer pricepointId;
    private String mcc;
    private String mnc;
    private String transactionUUID;
    private String userIdentifier;
    private String largeAccount;
    private String mnoDeliveryCode;
    private List<String> tags;
    private String trackingId;
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

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(final String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(final String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getLargeAccount() {
        return largeAccount;
    }

    public void setLargeAccount(final String largeAccount) {
        this.largeAccount = largeAccount;
    }

    public String getMnoDeliveryCode() {
        return mnoDeliveryCode;
    }

    public void setMnoDeliveryCode(final String mnoDeliveryCode) {
        this.mnoDeliveryCode = mnoDeliveryCode;
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

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(final String totalCharged) {
        this.totalCharged = totalCharged;
    }

    @Override
    public String toString() {
        return "NotificationDNRequest{" + "productId=" + productId + ", pricepointId=" + pricepointId + ", mcc='" + mcc
                + '\'' + ", mnc='" + mnc + '\'' + ", transactionUUID='" + transactionUUID + '\'' + ", userIdentifier='"
                + userIdentifier + '\'' + ", largeAccount='" + largeAccount + '\'' + ", mnoDeliveryCode='"
                + mnoDeliveryCode + '\'' + ", tags=" + tags + ", trackingId='" + trackingId + '\'' + ", totalCharged='"
                + totalCharged + '\'' + '}';
    }
}
