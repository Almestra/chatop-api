package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about a user returned by the API, without the password.
 *
 * @param id the id of the user
 * @param name the display name of the user
 * @param email the email address of the user
 * @param createdAt the date and time the account was created
 * @param updatedAt the date and time the account was last updated
 */
public record UserResponse(
        Integer id,
        String name,
        String email,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt) {

    /**
     * Creates the response from a user entity.
     *
     * @param user the user read from the database
     * @return the information about the user
     */
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

}
