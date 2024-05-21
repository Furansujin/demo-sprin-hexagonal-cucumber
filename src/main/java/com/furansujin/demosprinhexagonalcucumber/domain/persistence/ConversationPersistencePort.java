package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationPersistencePort {
    Conversation save(Conversation conversation);
    List<Conversation> findByProjectIdAndUserId(UUID projectId, UUID userId);
    Optional<Conversation> findByIdAndUserId(UUID idConversation, UUID userId);
    void delete(UUID id);
}