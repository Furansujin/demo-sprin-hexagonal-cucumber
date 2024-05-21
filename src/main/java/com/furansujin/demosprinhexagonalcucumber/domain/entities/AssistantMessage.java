package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.MessageSenderType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
public class AssistantMessage implements Message {

    private final UUID id;
    private final String originalText;
    private final LocalDateTime date;
    private final MessageSenderType type = MessageSenderType.ASSISTANT;

    @EqualsAndHashCode.Exclude
    private final Conversation conversation;

    public AssistantMessage(String originalText, Conversation conversation) {
        this.id = UUID.randomUUID();
        this.originalText = originalText;
        this.conversation = conversation;
        this.date = LocalDateTime.now();
    }


    @Override
    public String getText() {
        return originalText;
    }

    @Override
    public String getTextProcessed() {
        return originalText;
    }
}