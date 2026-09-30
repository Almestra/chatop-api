package com.chatop.api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Presentation of the API in the Swagger documentation, at {@code /swagger-ui.html}.
 *
 * <p>Declares the authentication by JSON Web Token, so that the protected endpoints
 * can be tried from Swagger UI with the {@code Authorize} button.
 */
@Configuration
@OpenAPIDefinition(info = @Info(
        title = "ChâTop API",
        version = "1.0.0",
        description = "Back-end of the ChâTop seasonal rental portal: accounts, rentals and messages to owners."))
@SecurityScheme(
        name = OpenApiConfig.BEARER_AUTH,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class OpenApiConfig {

    /**
     * Name of the security scheme, referenced by the {@code @SecurityRequirement}
     * annotations of the protected endpoints.
     */
    public static final String BEARER_AUTH = "bearerAuth";

}
