package com.chatop.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a request conflicts with existing data, for example
 * registering with an email address that is already used. Returns a 409 error.
 */
public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

}
