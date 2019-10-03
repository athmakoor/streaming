package com.streaming.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ConstraintViolationException extends RuntimeException {
    private String message;

    public ConstraintViolationException(final String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
