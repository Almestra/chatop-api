package com.chatop.api.exception;

import org.springframework.http.HttpStatusCode;

/**
 * Base class of the business errors returned to the client.
 *
 * <p>Each subclass defines its HTTP status code, and {@link GlobalExceptionHandler}
 * turns the exception into a response whose body is {@code { "message": "…" }}.
 */
public abstract class ApiException extends RuntimeException {

    /**
     * Creates the exception with the message sent to the client.
     *
     * @param message the error message
     */
    protected ApiException(String message) {
        super(message);
    }

    /**
     * Returns the HTTP status code of the error response.
     *
     * @return the status code matching this error
     */
    public abstract HttpStatusCode getHttpStatus();

}
