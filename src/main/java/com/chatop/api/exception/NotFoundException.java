package com.chatop.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when the requested rental, user or picture does not exist.
 * Returns a 404 error.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
