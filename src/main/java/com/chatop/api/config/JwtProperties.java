package com.chatop.api.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Settings of the JSON Web Tokens issued by the API, read from the {@code jwt.*} properties.
 *
 * @param secret the key used to sign and verify the tokens, at least 32 characters long
 * @param expiration how long a token remains valid, for example {@code 24h}
 */
@ConfigurationProperties(prefix = "jwt")
@Validated
public record JwtProperties(
        @NotNull @Size(min = 32) String secret,
        @NotNull Duration expiration) {

}
