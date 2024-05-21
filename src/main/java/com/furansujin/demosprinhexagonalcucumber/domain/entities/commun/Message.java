package com.furansujin.demosprinhexagonalcucumber.domain.entities.commun;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Message {
    UUID getId();
    String getText();
    String getTextProcessed();
    LocalDateTime getDate();
    MessageSenderType getType();

    Conversation getConversation();
}
