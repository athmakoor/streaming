package com.streaming.subscription.bean.jpa;

import com.streaming.UTCDateTimeConverter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "notifications")
public class NotificationEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "response_message",columnDefinition="LONGTEXT")
    private String responseMessage;

    @Column(name = "type")
    private String type;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "sync_type")
    private String syncType;

    @Column(name="provider")
    private String provider;

    @Column(name="partner")
    private String partner;

    @Column(name="price")
    private String price;

    @Column(name = "charge_status")
    private String chargeStatus;

    @Column(name = "click_id")
    private String clickId;

    @Column(name = "created_at")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdAt;

    @Column(name = "entry_channel")
    private String entryChannel;

    @Column(name = "total_charged")
    private String totalCharged;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getEntryChannel() {
        return entryChannel;
    }

    public void setEntryChannel(String entryChannel) {
        this.entryChannel = entryChannel;
    }

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(String totalCharged) {
        this.totalCharged = totalCharged;
    }
}
