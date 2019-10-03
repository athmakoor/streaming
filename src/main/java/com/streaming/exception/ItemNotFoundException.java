package com.streaming.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    private String message;

    public ItemNotFoundException(final String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
