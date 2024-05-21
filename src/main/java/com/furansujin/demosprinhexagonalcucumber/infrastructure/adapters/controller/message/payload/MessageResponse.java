package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.message.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.MessageSenderType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MessageResponse {

    private final UUID id;
    private final String originalText;
    private final LocalDateTime date;
    private final MessageSenderType type;

    public MessageResponse(UUID id, String originalText, LocalDateTime date, MessageSenderType type) {
        this.id = id;
        this.originalText = originalText;
        this.date = date;
        this.type = type;
    }


    public static MessageResponse fromDomain(Message message) {
        return new MessageResponse(message.getId(), message.getText(), message.getDate(), message.getType());
    }

    public static List<MessageResponse> fromDomain(List<Message> messages) {
        return  messages.stream().map(MessageResponse::fromDomain).toList();
    }
}
