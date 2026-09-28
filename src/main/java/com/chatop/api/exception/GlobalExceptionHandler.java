package com.chatop.api.exception;

import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chatop.api.dto.MessageResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Turns every exception raised while handling a request into a response
 * whose body is {@code { "message": "…" }}.
 *
 * <p>Errors raised by Spring MVC (unreadable body, invalid field, unknown URL…)
 * keep the status code chosen by Spring, business errors use the status code
 * of their {@link ApiException}, and any other exception becomes a 500 error.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Invalid request";

    /**
     * Formats the errors raised by Spring MVC, keeping the status code
     * and headers chosen by Spring.
     */
    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        String message = DEFAULT_MESSAGE;
        HttpStatus status = HttpStatus.resolve(statusCode.value());

        if (body instanceof ProblemDetail problemDetail && problemDetail.getDetail() != null) {
            message = problemDetail.getDetail();
        } else if (status != null) {
            message = status.getReasonPhrase();
        }

        return buildResponse(statusCode, headers, message);
    }

    /**
     * Lists the invalid fields of a request body annotated with {@code @Valid},
     * for example {@code email: must be a well-formed email address}.
     */
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(status, headers, message);
    }

    /**
     * Returns the status code and message of a business exception.
     *
     * @param ex the business exception
     * @return the error response
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        return buildResponse(ex.getHttpStatus(), new HttpHeaders(), ex.getMessage());
    }

    /**
     * Logs any other exception and returns a generic 500 error,
     * so that no technical detail reaches the client.
     *
     * @param ex the unexpected exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        log.error("Unexpected error", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, new HttpHeaders(), "Unexpected error");
    }

    private ResponseEntity<Object> buildResponse(HttpStatusCode status, HttpHeaders headers, String message) {
        return ResponseEntity.status(status)
                .headers(headers)
                .body(new MessageResponse(message));
    }

}
