package com.chatop.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.config.OpenApiConfig;
import com.chatop.api.dto.MessageRequest;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.service.MessageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/**
 * Message endpoints of the API.
 */
@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sends a message to the owner of a rental.
     *
     * @param request the rental, the author and the text of the message
     * @param jwt the token of the request, whose subject is the id of the logged-in user
     * @return the confirmation message displayed by the front-end
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@Valid @RequestBody MessageRequest request, @AuthenticationPrincipal Jwt jwt) {
        messageService.sendMessage(request, Integer.valueOf(jwt.getSubject()));
        return new MessageResponse("Message send with success");
    }

}
