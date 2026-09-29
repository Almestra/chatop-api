package com.chatop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.entity.Rental;

/**
 * Data access for {@link Rental} entities.
 *
 * <p>The standard operations (save, find, delete…) come from {@link JpaRepository}.
 */
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
