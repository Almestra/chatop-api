package com.chatop.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.service.RentalService;

import jakarta.validation.Valid;

/**
 * Rental endpoints of the API.
 */
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * Returns all the rentals.
     *
     * @return the list of rentals
     */
    @GetMapping
    public RentalsResponse getRentals() {
        return rentalService.getRentals();
    }

    /**
     * Returns a rental.
     *
     * @param id the id of the rental
     * @return the information about the rental
     */
    @GetMapping("/{id}")
    public RentalResponse getRental(@PathVariable Integer id) {
        return rentalService.getRental(id);
    }

    /**
     * Creates a rental owned by the logged-in user, from a form sent
     * as {@code multipart/form-data}.
     *
     * @param request the fields and the picture of the rental
     * @param jwt the token of the request, whose subject is the id of the owner
     * @return the confirmation message displayed by the front-end
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createRental(@Valid @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        rentalService.createRental(request, Integer.valueOf(jwt.getSubject()));
        return new MessageResponse("Rental created !");
    }

    /**
     * Updates a rental of the logged-in user, from a form sent
     * as {@code multipart/form-data}. A picture sent with the form is ignored.
     *
     * @param id the id of the rental
     * @param request the new values of the rental
     * @param jwt the token of the request, whose subject is the id of the logged-in user
     * @return the confirmation message displayed by the front-end
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse updateRental(
            @PathVariable Integer id,
            @Valid @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        rentalService.updateRental(id, request, Integer.valueOf(jwt.getSubject()));
        return new MessageResponse("Rental updated !");
    }

}
