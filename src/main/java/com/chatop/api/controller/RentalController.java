package com.chatop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.service.RentalService;

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

}
