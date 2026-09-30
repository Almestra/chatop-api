package com.chatop.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.api.dto.RentalRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.entity.Rental;
import com.chatop.api.exception.ForbiddenException;
import com.chatop.api.exception.NotFoundException;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

/**
 * Handles the rentals.
 *
 * <p>The methods run in a transaction, so that the owner of a rental, loaded lazily,
 * stays accessible until the rental is converted into a response.
 */
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, ImageService imageService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
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

    /**
     * Creates a rental owned by the given user.
     *
     * <p>The picture is stored first. If the rental cannot be saved, the picture
     * is deleted, so that no orphan file remains.
     *
     * @param request the fields and the picture of the rental
     * @param ownerId the id of the logged-in user, who becomes the owner
     */
    @Transactional
    public void createRental(RentalRequest request, Integer ownerId) {
        String pictureUrl = imageService.store(request.picture());

        try {
            Rental rental = new Rental();
            rental.setName(request.name());
            rental.setSurface(request.surface());
            rental.setPrice(request.price());
            rental.setDescription(request.description());
            rental.setPicture(pictureUrl);
            rental.setOwner(userRepository.getReferenceById(ownerId));

            rentalRepository.save(rental);
        } catch (RuntimeException e) {
            imageService.delete(pictureUrl);
            throw e;
        }
    }

    /**
     * Updates the name, surface, price and description of a rental.
     *
     * <p>Only the owner can update a rental, and its picture never changes.
     * No call to {@code save} is needed: Hibernate writes the changes of the loaded
     * rental when the transaction ends.
     *
     * @param id the id of the rental
     * @param request the new values of the rental, whose picture is ignored
     * @param userId the id of the logged-in user
     * @throws NotFoundException if no rental has this id
     * @throws ForbiddenException if the logged-in user is not the owner of the rental
     */
    @Transactional
    public void updateRental(Integer id, RentalRequest request, Integer userId) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found"));

        if (!rental.getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Only the owner can update this rental");
        }

        rental.setName(request.name());
        rental.setSurface(request.surface());
        rental.setPrice(request.price());
        rental.setDescription(request.description());
    }

}
