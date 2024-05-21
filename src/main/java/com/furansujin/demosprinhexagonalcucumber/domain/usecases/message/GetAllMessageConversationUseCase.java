package com.furansujin.demosprinhexagonalcucumber.domain.usecases.message;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.ConversationNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;

import java.util.List;
import java.util.UUID;

public class GetAllMessageConversationUseCase implements UseCase<List<Message>, GetAllMessageConversationUseCase.GetAllMessageConversationRequest> {

    private final ConversationPersistencePort conversationPersistencePort;
    private final AuthenticationGateway authenticationGateway;

    public GetAllMessageConversationUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.conversationPersistencePort = conversationPersistencePort;

    }
    @Override
    public List<Message> execute(GetAllMessageConversationRequest arg) {
        User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        Conversation conversation = this.conversationPersistencePort.findByIdAndUserId(arg.conversationId(), user.getId()).orElseThrow(() -> new ConversationNotFoundException(arg.conversationId()));
        return conversation.getMessages();
    }

    public record GetAllMessageConversationRequest(UUID conversationId) {
    }
}
