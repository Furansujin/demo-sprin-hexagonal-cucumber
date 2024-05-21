package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryMessagePersistenceAdapter implements MessagePersistencePort {

    private Set<Message> messages = new HashSet<>();


    @Override
    public UserMessage saveUserMessage(UserMessage message) {
        this.messages.add(message);
        return message;
    }

    @Override
    public AssistantMessage saveAssistantMessage(AssistantMessage message) {
        this.messages.add(message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return this.messages.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    @Override
    public void saveALL(List<Message> userMessage) {
        this.messages.addAll(userMessage);
    }


    public void delete(UUID id) {
        this.messages.removeIf(m -> m.getId().equals(id));
    }

    public List<Message> findAllByConversationId(UUID conversationId) {
        return this.messages.stream().filter(m -> m.getConversation().getId().equals(conversationId)).toList();
    }
}
