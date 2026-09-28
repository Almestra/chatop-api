package com.chatop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.entity.User;

/**
 * Data access for {@link User} entities.
 *
 * <p>The standard operations (save, find, delete…) come from {@link JpaRepository}.
 * Spring Data implements the query methods declared here from their names.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by email address.
     *
     * @param email the email address to look for
     * @return the matching user, or an empty {@code Optional} if none exists
     */
    Optional<User> findByEmail(String email);

    /**
     * Tells whether a user already uses the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user already has this email address
     */
    boolean existsByEmail(String email);

}
