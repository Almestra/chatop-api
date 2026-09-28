package com.chatop.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when the email address or password sent to log in is wrong.
 * Returns a 401 error.
 */
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
