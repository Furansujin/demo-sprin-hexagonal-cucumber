package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.message.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageRequest {

    private UUID idConversation;
    private UUID idProject;
    private String originalText;


}
