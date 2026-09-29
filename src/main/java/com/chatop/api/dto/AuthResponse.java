package com.chatop.api.dto;

/**
 * Response of a successful registration or login.
 *
 * @param token the signed JSON Web Token to send in the {@code Authorization} header
 */
public record AuthResponse(String token) {

}
