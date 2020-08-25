package com.streaming.subscription.service;

import javax.servlet.http.HttpServletRequest;

public interface NotificationsService {
    void save(String type, HttpServletRequest request);
}
