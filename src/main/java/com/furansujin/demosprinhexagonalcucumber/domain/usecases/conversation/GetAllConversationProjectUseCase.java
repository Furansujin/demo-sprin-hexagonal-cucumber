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

import java.util.List;
import java.util.UUID;

public class GetAllConversationProjectUseCase implements UseCase<List<Conversation>, GetAllConversationProjectUseCase. GetAllConversationRequest> {
    private final ConversationPersistencePort conversationPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;

    public GetAllConversationProjectUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort, ProjectPersistencePort projectPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.conversationPersistencePort = conversationPersistencePort;
        this.projectPersistencePort = projectPersistencePort;
    }

    @Override
    public List<Conversation> execute(GetAllConversationRequest arg)   {
        User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        Project project = this.projectPersistencePort.findByIdAndUserId(arg.projectId(), user.getId()).orElseThrow(() -> new ProjectNotFoundException(arg.projectId()));
        return this.conversationPersistencePort.findByProjectIdAndUserId(project.getId(), user.getId());
    }


    public record GetAllConversationRequest(UUID projectId) {
    }
}
