package com.chatop.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when the authenticated user is not allowed to perform an action,
 * for example updating a rental they do not own. Returns a 403 error.
 */
public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

}
