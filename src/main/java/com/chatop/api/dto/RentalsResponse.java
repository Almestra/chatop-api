package com.chatop.api.dto;

import java.util.List;

/**
 * List of rentals returned by the API, wrapped in an object
 * because the front-end expects {@code { "rentals": [ … ] }}.
 *
 * @param rentals the rentals
 */
public record RentalsResponse(List<RentalResponse> rentals) {

}
