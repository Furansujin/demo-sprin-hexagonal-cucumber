package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.conversation.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreateConversationRequest {

    @NotNull
    private final UUID projectId;
    @NotNull
    private final String title;

    public CreateConversationRequest(UUID projectId, String title) {
        this.projectId = projectId;
        this.title = title;
    }
}
