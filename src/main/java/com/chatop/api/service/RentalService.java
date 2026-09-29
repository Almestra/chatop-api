package com.chatop.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.exception.NotFoundException;
import com.chatop.api.repository.RentalRepository;

/**
 * Handles the rentals.
 *
 * <p>The methods run in a transaction, so that the owner of a rental, loaded lazily,
 * stays accessible until the rental is converted into a response.
 */
@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    /**
     * Returns all the rentals.
     *
     * @return the list of rentals
     */
    @Transactional(readOnly = true)
    public RentalsResponse getRentals() {
        List<RentalResponse> rentals = rentalRepository.findAll()
                .stream()
                .map(RentalResponse::from)
                .toList();

        return new RentalsResponse(rentals);
    }

    /**
     * Finds a rental by id.
     *
     * @param id the id of the rental
     * @return the information about the rental
     * @throws NotFoundException if no rental has this id
     */
    @Transactional(readOnly = true)
    public RentalResponse getRental(Integer id) {
        return rentalRepository.findById(id)
                .map(RentalResponse::from)
                .orElseThrow(() -> new NotFoundException("Rental not found"));
    }

}
