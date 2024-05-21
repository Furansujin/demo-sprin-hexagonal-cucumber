package com.furansujin.demosprinhexagonalcucumber.acceptance.message;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.GetAllMessageConversationUseCase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetAllMessageConversationStepdefs {

 private final GetAllMessageConversationUseCase getAllMessageConversationUseCase;
    private final ConversationPersistencePort conversationPersistencePort;

    private String messageError;
    List<Message> messages;

    public GetAllMessageConversationStepdefs(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort) {
        this.getAllMessageConversationUseCase = new GetAllMessageConversationUseCase(authenticationGateway, conversationPersistencePort);
        this.conversationPersistencePort = conversationPersistencePort;
        this.messages = new ArrayList<>();
        this.messageError = "";

    }


    @When("the user requests to see all messages for the conversation with ID {string}")
    public void theUserRequestsToSeeAllMessagesForTheConversationWithID(String idConversation) {
        try {
            this.messages = getAllMessageConversationUseCase.execute(new GetAllMessageConversationUseCase.GetAllMessageConversationRequest(UUID.fromString(idConversation)));
        } catch (Exception e) {
            messageError = e.getMessage();
        }
    }

    @Then("all messages for the conversation with ID {string} should be returned")
    public void allMessagesForTheConversationWithIDShouldBeReturned(String idConversation) {

        assertTrue(this.messages.size() > 0);
        assertTrue(this.messages.stream().allMatch(message -> message.getConversation().getId().equals(UUID.fromString(idConversation))));
    }

    @Then("an error message {string} should be returned for conversation ID {string}")
    public void anErrorMessageShouldBeReturnedForConversationID(String message, String idConversation) {
        assertTrue(messageError != "");
        assertTrue(messageError.contains(message));
        assertTrue(messageError.contains(idConversation));
    }

    @Then("an error message {string} should be returned when requests to see all messages")
    public void anErrorMessageShouldBeReturnedWhenRequestsToSeeAllMessages(String message) {
        assertTrue(messageError != "");
        assertTrue(messageError.contains(message));
    }
}
