package com.streaming.aggregator.timwe.bean.notication;

/**
 * Created by HARIKRISHNA on 2/2/2021.
 */
public class SubscriptionUpdateModel {
    private String updateType;
    private String status;
    private String clickId;
    private String packId;
    private String msisdn;
    private String statusMessage;
    private String totalCharged;

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(final String packId) {
        this.packId = packId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(String totalCharged) {
        this.totalCharged = totalCharged;
    }
}
