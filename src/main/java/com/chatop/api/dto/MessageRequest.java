package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Body of a request that sends a message to the owner of a rental.
 *
 * @param rentalId the id of the rental, sent as {@code rental_id}
 * @param userId the id of the author, sent as {@code user_id}, which must be the logged-in user
 * @param message the text of the message, at most 2000 characters
 */
public record MessageRequest(
        @NotNull @JsonProperty("rental_id") Integer rentalId,
        @NotNull @JsonProperty("user_id") Integer userId,
        @NotBlank @Size(max = 2000) String message) {

}
