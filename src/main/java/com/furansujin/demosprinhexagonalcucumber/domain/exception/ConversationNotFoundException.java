package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class ConversationNotFoundException extends RuntimeException {
    public ConversationNotFoundException(UUID id) {
        super("Conversation not found with ID: " + id);
    }
}