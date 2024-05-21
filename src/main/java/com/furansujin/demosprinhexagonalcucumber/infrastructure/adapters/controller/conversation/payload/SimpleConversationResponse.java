package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.conversation.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SimpleConversationResponse {


    private final UUID id;

    private final String title;

    public SimpleConversationResponse(UUID id, String title) {
        this.id = id;
        this.title = title;
    }


    public static SimpleConversationResponse fromDomain(Conversation conversation) {
        return new SimpleConversationResponse(conversation.getId(), conversation.getTitle());
    }

    public static List<SimpleConversationResponse> fromDomain(List<Conversation> conversations) {
        return  conversations.stream().map(SimpleConversationResponse::fromDomain).toList();
    }
}
