package com.furansujin.demosprinhexagonalcucumber.acceptance.conversation;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation.GetAllConversationProjectUseCase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class GetAllConversationProjectStepdefs {

    private final ConversationPersistencePort conversationPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;
    private final GetAllConversationProjectUseCase getAllConversationProjectUseCase;

    private String messageError;

    List<Conversation> conversations;

    public GetAllConversationProjectStepdefs(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort, ProjectPersistencePort projectPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.conversationPersistencePort = conversationPersistencePort;
        this.projectPersistencePort = projectPersistencePort;
        this.getAllConversationProjectUseCase = new GetAllConversationProjectUseCase(authenticationGateway, conversationPersistencePort, projectPersistencePort);
        this.conversations = null;
        this.messageError = "";
    }



    @When("the user requests to see all conversations for the project with ID {string}")
    public void theUserRequestsToSeeAllConversationsForTheProjectWithID(String idProject) {
        try {
            this.conversations  = getAllConversationProjectUseCase.execute(new GetAllConversationProjectUseCase.GetAllConversationRequest(UUID.fromString(idProject)));
        } catch (Exception e) {
            this.messageError = e.getMessage();
        }
    }

    @Then("all conversations for the project with ID {string} should be returned")
    public void allConversationsForTheProjectWithIDShouldBeReturned(String idProject) {
        assertTrue(this.conversations != null);
        assertTrue(!this.conversations.isEmpty());
        assertTrue(this.conversations.stream().allMatch(c -> c.getProject().getId().equals(UUID.fromString(idProject))));

    }

    @Then("an error message {string} should be returned for project ID {string}")
    public void anErrorMessageShouldBeReturnedForProjectID(String messageError, String idProject) {
        assertTrue(this.messageError.contains(messageError+idProject));
    }


    @Then("an error message {string} should be returned when trying to retrieve conversations")
    public void anErrorMessageShouldBeReturnedWhenTryingToRetrieveConversations(String message) {

        assertTrue(this.messageError.contains(message));
    }
}
