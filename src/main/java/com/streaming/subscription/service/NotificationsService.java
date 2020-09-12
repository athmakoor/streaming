package com.streaming.subscription.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public interface NotificationsService {
    void save(String type, HttpServletRequest request) throws UnsupportedEncodingException;

    void create();
}
