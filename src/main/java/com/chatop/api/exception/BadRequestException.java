package com.chatop.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a request is well formed but cannot be processed, for example
 * a message about an unknown rental or an invalid picture. Returns a 400 error.
 */
public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
