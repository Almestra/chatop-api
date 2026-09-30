package com.chatop.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.entity.Message;
import com.chatop.api.entity.Rental;
import com.chatop.api.exception.BadRequestException;
import com.chatop.api.exception.ForbiddenException;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

/**
 * Handles the messages sent to the owners of the rentals.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    /**
     * Saves a message from the logged-in user about a rental.
     *
     * <p>The front-end sends the id of the author: it is checked against the token
     * instead of being trusted.
     *
     * @param request the rental, the author and the text of the message
     * @param userId the id of the logged-in user
     * @throws ForbiddenException if the author is not the logged-in user
     * @throws BadRequestException if the rental does not exist
     */
    @Transactional
    public void sendMessage(MessageRequest request, Integer userId) {
        if (!request.userId().equals(userId)) {
            throw new ForbiddenException("user_id must match the logged-in user");
        }

        Rental rental = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new BadRequestException("Unknown rental"));

        Message message = new Message();
        message.setRental(rental);
        message.setUser(userRepository.getReferenceById(userId));
        message.setMessage(request.message());

        messageRepository.save(message);
    }

}
