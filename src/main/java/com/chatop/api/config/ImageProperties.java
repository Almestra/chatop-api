package com.chatop.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Settings of the rental pictures, read from the {@code images.*} properties.
 *
 * @param directory the folder where the pictures are stored, for example {@code uploads}
 * @param baseUrl the start of the picture URLs, for example {@code http://localhost:3001/api/images}
 */
@ConfigurationProperties(prefix = "images")
@Validated
public record ImageProperties(
        @NotBlank String directory,
        @NotBlank String baseUrl) {

}
