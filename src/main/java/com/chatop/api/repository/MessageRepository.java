package com.chatop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.entity.Message;

/**
 * Data access for {@link Message} entities.
 *
 * <p>The standard operations (save, find, delete…) come from {@link JpaRepository}.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
