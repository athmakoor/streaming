package com.streaming.aggregator.timwe.bean;

public class UnSubscribeRequest {
    private String userIdentifier;
    private String userIdentifierType;
    private String productId;
    private String mcc;
    private String mnc;
    private String entryChannel;
    private String largeAccount;
    private String subKeyword;
    private String trackingId = "";
    private String clientIp = "";

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(final String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getUserIdentifierType() {
        return userIdentifierType;
    }

    public void setUserIdentifierType(final String userIdentifierType) {
        this.userIdentifierType = userIdentifierType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
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

    public String getEntryChannel() {
        return entryChannel;
    }

    public void setEntryChannel(final String entryChannel) {
        this.entryChannel = entryChannel;
    }

    public String getLargeAccount() {
        return largeAccount;
    }

    public void setLargeAccount(final String largeAccount) {
        this.largeAccount = largeAccount;
    }

    public String getSubKeyword() {
        return subKeyword;
    }

    public void setSubKeyword(final String subKeyword) {
        this.subKeyword = subKeyword;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(final String trackingId) {
        this.trackingId = trackingId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(final String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String toString() {
        return "UnSubscribeRequest{" + "userIdentifier='" + userIdentifier + '\'' + ", userIdentifierType='"
                + userIdentifierType + '\'' + ", productId='" + productId + '\'' + ", mcc='" + mcc + '\'' + ", mnc='"
                + mnc + '\'' + ", entryChannel='" + entryChannel + '\'' + ", largeAccount='" + largeAccount + '\''
                + ", subKeyword='" + subKeyword + '\'' + ", trackingId='" + trackingId + '\'' + ", clientIp='"
                + clientIp + '\'' + '}';
    }
}
