package com.streaming.utils.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestException.class);
    public RequestException(final String message) {
        super(message);
        LOGGER.error(message);
    }
}
