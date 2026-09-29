package com.chatop.api.service;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.exception.NotFoundException;
import com.chatop.api.repository.UserRepository;

/**
 * Retrieves user information.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by id.
     *
     * @param id the id of the user
     * @return the information about the user
     * @throws NotFoundException if no user has this id
     */
    public UserResponse getUser(Integer id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

}
