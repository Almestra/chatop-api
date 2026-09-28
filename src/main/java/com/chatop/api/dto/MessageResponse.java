package com.chatop.api.dto;

/**
 * Response body holding a single message, used both for confirmations
 * (for example {@code "Rental created !"}) and for errors.
 *
 * @param message the text returned to the client
 */
public record MessageResponse(String message) {

}
