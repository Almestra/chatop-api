package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.entity.Rental;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rental returned by the API.
 *
 * @param id the id of the rental
 * @param name the name of the rental
 * @param surface the surface, in square meters
 * @param price the price per night
 * @param picture the absolute URL of the picture
 * @param description the description of the rental
 * @param ownerId the id of the user who owns the rental
 * @param createdAt the date and time the rental was created
 * @param updatedAt the date and time the rental was last updated
 */
public record RentalResponse(
        Integer id,
        String name,
        Integer surface,
        Integer price,
        String picture,
        String description,
        @JsonProperty("owner_id") Integer ownerId,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt) {

    /**
     * Creates the response from a rental entity.
     *
     * @param rental the rental read from the database
     * @return the information about the rental
     */
    public static RentalResponse from(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt());
    }

}
