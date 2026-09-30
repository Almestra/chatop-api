package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Form sent to create or update a rental, as {@code multipart/form-data}.
 *
 * @param name the name of the rental
 * @param surface the surface, in square meters
 * @param price the price per night
 * @param description the description of the rental
 * @param picture the picture file, required on creation and ignored on update
 */
public record RentalRequest(
        @NotBlank @Size(max = 255) String name,
        @NotNull @Positive Integer surface,
        @NotNull @Positive Integer price,
        @NotBlank @Size(max = 2000) String description,
        MultipartFile picture) {

}
