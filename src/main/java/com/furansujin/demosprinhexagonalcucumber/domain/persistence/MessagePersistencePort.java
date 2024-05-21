package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessagePersistencePort {
    UserMessage saveUserMessage(UserMessage message );
    AssistantMessage saveAssistantMessage(AssistantMessage message );
    Optional<Message> findById(UUID id);

    void saveALL(List<Message> userMessage);
}