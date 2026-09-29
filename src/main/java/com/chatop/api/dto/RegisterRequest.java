package com.chatop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Body of a registration request.
 *
 * @param name the display name of the new user
 * @param email the email address used to log in, which must not belong to another account
 * @param password the password in clear text, at most 72 characters (BCrypt limit)
 */
public record RegisterRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(max = 72) String password) {

}
