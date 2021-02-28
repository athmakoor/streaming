package com.streaming.aggregator.timwe.service;

import com.streaming.aggregator.timwe.bean.notication.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Hari on 1/29/2021.
 */
public interface TimWeNotificationService {

    NotificationResponse save(HttpServletRequest request, NotificationUserOptinRequest data, String provide, String subType);

    NotificationResponse save(HttpServletRequest request, NotificationUserOptoutRequest data, String provide, String subType);

    NotificationResponse save(HttpServletRequest request, NotificationDNRequest data, String provide, String subType);

    NotificationResponse save(HttpServletRequest request, NotificationMoRequest data, String provide, String subType);

    NotificationResponse save(HttpServletRequest request, NotificationChargeRequest data, String provide, String subType);
}
