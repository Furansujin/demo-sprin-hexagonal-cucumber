package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.message;


import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.CreateMessageUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.GetAllMessageConversationUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.message.payload.MessageRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.message.payload.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {


    private final GetAllMessageConversationUseCase getAllMessageConversationUseCase;

    private final CreateMessageUseCase createMessageUseCase;

    @GetMapping("/conversation/{conversationId}")
    public List<MessageResponse> getAllMessageByIdConversation(@PathVariable UUID conversationId) throws Exception {
        return MessageResponse.fromDomain(getAllMessageConversationUseCase.execute(new GetAllMessageConversationUseCase.GetAllMessageConversationRequest(conversationId)));
    }


    @PostMapping("/send")
    public List<MessageResponse> createMessage(@RequestBody MessageRequest messageRequest) throws Exception {

        return MessageResponse.fromDomain(createMessageUseCase.execute(new CreateMessageUseCase.CreateMessageRequest(messageRequest.getOriginalText(), messageRequest.getIdConversation(), messageRequest.getIdProject())));
    }

}
