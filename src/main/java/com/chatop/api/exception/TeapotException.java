package com.chatop.api.exception;

import org.springframework.http.HttpStatusCode;

/**
 * Just a teapot.
 *
 * <p>It can serve tea, but coffee is strictly outside its
 * architectural boundaries. Any attempt to brew coffee will be
 * rejected with the dignity expected from an RFC-compliant teapot.
 */
public class TeapotException extends ApiException {

    public TeapotException(String message) {
        super(message);
    }

    @Override
    public HttpStatusCode getHttpStatus() {
        return HttpStatusCode.valueOf(418);
    }

}
