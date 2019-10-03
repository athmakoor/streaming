package com.streaming.web.service;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class WebService {

    public String generateStudioId() {
        return UUID.randomUUID().toString();
    }

    public void updateDefaultModel(final Map<String, Object> model) {
        /*boolean debug = PropertyManager.isDebug();
        boolean prod = PropertyManager.isProd();

        model.put("DEBUG", debug);
        model.put("PROD", prod);*/
    }

    public String getFullURL(final HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
