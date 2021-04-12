package com.streaming.aggregator.timwe.bean.notication;

import java.util.List;

/**
 * Created by HARIKRISHNA on 2/21/2021.
 */
public class NotificationChargeRequest {
    private Integer productId;
    private Integer pricepointId;
    private String mcc;
    private String mnc;
    private String msisdn;
    private String largeAccount;
    private String entryChannel;
    private String text;
    private List<String> tags;
    private String transactionUUID;
    private String trackingId;
    private String totalCharged;
    private String mnoDeliveryCode;
    private String userIdentifier;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPricepointId() {
        return pricepointId;
    }

    public void setPricepointId(Integer pricepointId) {
        this.pricepointId = pricepointId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLargeAccount() {
        return largeAccount;
    }

    public void setLargeAccount(String largeAccount) {
        this.largeAccount = largeAccount;
    }

    public String getEntryChannel() {
        return entryChannel;
    }

    public void setEntryChannel(String entryChannel) {
        this.entryChannel = entryChannel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(String transactionUUID) {
        this.transactionUUID = transactionUUID;
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

    public void setTotalCharged(String totalCharged) {
        this.totalCharged = totalCharged;
    }

    public String getMnoDeliveryCode() {
        return mnoDeliveryCode;
    }

    public void setMnoDeliveryCode(String mnoDeliveryCode) {
        this.mnoDeliveryCode = mnoDeliveryCode;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    @Override
    public String toString() {
        return "NotificationChargeRequest{" +
                "productId=" + productId.toString() +
                ", pricepointId=" + pricepointId +
                ", mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", largeAccount='" + largeAccount + '\'' +
                ", entryChannel='" + entryChannel + '\'' +
                ", text='" + text + '\'' +
                ", tags=" + tags +
                ", transactionUUID='" + transactionUUID + '\'' +
                ", trackingId='" + trackingId + '\'' +
                ", totalCharged='" + totalCharged + '\'' +
                ", mnoDeliveryCode='" + mnoDeliveryCode + '\'' +
                ", userIdentifier='" + userIdentifier + '\'' +
                '}';
    }
}
