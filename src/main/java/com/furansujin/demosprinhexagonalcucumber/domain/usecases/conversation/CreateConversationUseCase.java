package com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.ProjectNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;

import java.util.UUID;

public class CreateConversationUseCase implements UseCase<Conversation, CreateConversationUseCase.CreateConversationRequest> {

    private final ConversationPersistencePort conversationPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;

    public CreateConversationUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort, ProjectPersistencePort projectPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.conversationPersistencePort = conversationPersistencePort;
        this.projectPersistencePort = projectPersistencePort;
    }

    @Override
    public Conversation execute(CreateConversationRequest arg) throws Exception {

        User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        Project project = this.projectPersistencePort.findByIdAndUserId(arg.projectId(), user.getId()).orElseThrow(() -> new ProjectNotFoundException(arg.projectId()));
        Conversation conversation = Conversation.create(user, project, arg.title());
       return conversationPersistencePort.save(conversation);

    }

    public record CreateConversationRequest(UUID projectId, String title) {
    }
}
