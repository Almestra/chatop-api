package com.chatop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Body of a login request.
 *
 * @param email the email address of the account
 * @param password the password in clear text, at most 72 characters (BCrypt limit)
 */
public record LoginRequest(
        @NotBlank String email,
        @NotBlank @Size(max = 72) String password) {

}
