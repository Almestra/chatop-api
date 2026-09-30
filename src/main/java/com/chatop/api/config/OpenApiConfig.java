package com.chatop.api.config;

import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Presentation of the API in the Swagger documentation, at {@code /swagger-ui.html}.
 *
 * <p>Declares the authentication by JSON Web Token, so that the protected endpoints
 * can be tried from Swagger UI with the {@code Authorize} button, and lists the groups
 * of endpoints in the order of the API definition.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "ChâTop API", version = "1.0.0", description = """
        Back-end of the ChâTop seasonal rental portal: accounts, rentals and messages to owners.

        Every error returns `{ "message": "…" }`. The status codes of each endpoint are listed \
        in the [API definition](https://github.com/Almestra/chatop-api/blob/main/docs/api-definition.md).
        """))
@SecurityScheme(name = OpenApiConfig.BEARER_AUTH, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

    /**
     * Name of the security scheme, referenced by the {@code @SecurityRequirement}
     * annotations of the protected endpoints.
     */
    public static final String BEARER_AUTH = "bearerAuth";

    /**
     * Lists the groups of endpoints, with their description, in the order of the API definition.
     *
     * <p>springdoc builds this list in an arbitrary order, so it is replaced once the documentation
     * is generated. The controllers join these groups by name, with {@code @Tag}.
     *
     * @return the customizer that sets the groups
     */
    @Bean
    public OpenApiCustomizer endpointGroups() {
        return openApi -> openApi.setTags(List.of(
                new Tag().name("Authentication").description("Registration, login and logged-in user"),
                new Tag().name("Rentals").description("Rentals, created and updated by their owners"),
                new Tag().name("Messages").description("Messages to the owners of the rentals"),
                new Tag().name("Users").description("Information about the users, such as the owners of the rentals"),
                new Tag().name("Pictures")
                        .description("Rental pictures, public because <img> tags never send the token")));
    }

}
