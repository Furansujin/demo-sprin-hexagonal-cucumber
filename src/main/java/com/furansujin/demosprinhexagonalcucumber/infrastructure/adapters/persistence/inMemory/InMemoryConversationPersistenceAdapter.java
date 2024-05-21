package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public class InMemoryConversationPersistenceAdapter implements ConversationPersistencePort {

    private Set<Conversation> conversations = new HashSet<>();

    private final InMemoryMessagePersistenceAdapter messagePersistencePort;

    public InMemoryConversationPersistenceAdapter(MessagePersistencePort messagePersistencePort) {
        this.messagePersistencePort = (InMemoryMessagePersistenceAdapter) messagePersistencePort;
    }
    @Override
    public Conversation save(Conversation conversation) {
        this.conversations.removeIf(c -> c.getId().equals(conversation.getId()));
       this.conversations.add(conversation);
        return conversation;
    }

    @Override
    public List<Conversation> findByProjectIdAndUserId(UUID projectId, UUID userId) {
        return  this.conversations.stream().filter(c -> c.getProject().getId().equals(projectId) && c.getUser().getId().equals(userId))
                .map(c -> {
                    c.setMessages(this.messagePersistencePort.findAllByConversationId(c.getId()));
                    return c;
                })
                .toList();
    }
    @Override
    public Optional<Conversation> findByIdAndUserId(UUID idConversation, UUID userId) {
        try {
            return this.conversations.stream().filter(c -> c.getId().equals(idConversation) && c.getUser().getId().equals(userId))
                    .map(c -> {
                        c.setMessages(this.messagePersistencePort.findAllByConversationId(c.getId()));
                        return c;
                    })
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public void delete(UUID id) {
        this.conversations.removeIf(c -> c.getId().equals(id));
    }

    public List<Conversation> findByProjectId(UUID id) {
        return this.conversations.stream().filter(c -> c.getProject().getId().equals(id))
                .map(c -> {
                    c.setMessages(this.messagePersistencePort.findAllByConversationId(c.getId()));
                    return c;
                })
                .toList();
    }

    public Optional<Conversation> findById(UUID uuid) {
        return this.conversations.stream().filter(c -> c.getId().equals(uuid))
                .map(c -> {
                    c.setMessages(this.messagePersistencePort.findAllByConversationId(c.getId()));
                    return c;
                })
                .findFirst();
    }
}
