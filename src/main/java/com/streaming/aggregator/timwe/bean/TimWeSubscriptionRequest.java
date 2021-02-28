package com.streaming.aggregator.timwe.bean;

public class TimWeSubscriptionRequest {
    private String roleId;

    private String msisdn;

    private String productId;

    private String pricepointId;

    private String correlatorId;

    private String packId;

    private String currency;

    private String price;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getCorrelatorId() {
        return correlatorId;
    }

    public void setCorrelatorId(String correlatorId) {
        this.correlatorId = correlatorId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TimWeSubscriptionRequest{" + "roleId='" + roleId + '\'' + ", msisdn='" + msisdn + '\'' + ", productId='"
                + productId + '\'' + ", pricepointId='" + pricepointId + '\'' + ", correlatorId='" + correlatorId + '\''
                + ", packId='" + packId + '\'' + ", currency='" + currency + '\'' + ", price='" + price + '\'' + '}';
    }
}
