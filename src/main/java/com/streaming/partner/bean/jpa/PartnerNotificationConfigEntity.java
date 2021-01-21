package com.streaming.partner.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "partner_notification_config")
public class PartnerNotificationConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "notification_percent")
    private Double notificationPercent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Double getNotificationPercent() {
        return notificationPercent;
    }

    public void setNotificationPercent(Double notificationPercent) {
        this.notificationPercent = notificationPercent;
    }
}
