package com.streaming.subscription.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.streaming.subscription.bean.jpa.NotificationEntity;
import com.streaming.subscription.repository.NotificationsRepository;
import com.streaming.subscription.service.NotificationsService;
import com.streaming.utils.TimeUtil;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    @Resource
    private NotificationsRepository notificationsRepository;


    @Override
    public void save(String type, HttpServletRequest request) {
        NotificationEntity entity = new NotificationEntity();

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(request.getQueryString());
        entity.setType(type);

        if ("notification".equals(type)) {
            String chargeStatus = request.getParameter("charg_status");
            entity.setChargeStatus(chargeStatus);
        }

        notificationsRepository.save(entity);
    }
}
