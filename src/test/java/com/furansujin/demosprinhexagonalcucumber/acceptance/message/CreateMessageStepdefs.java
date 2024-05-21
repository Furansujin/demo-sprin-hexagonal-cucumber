package com.furansujin.demosprinhexagonalcucumber.acceptance.message;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.MessageSenderType;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.CreateMessageUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateMessageStepdefs {


    private final CreateMessageUseCase createMessageUseCase;
    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;

    private final ConversationPersistencePort conversationPersistencePort;

    private boolean messageFailed = false;

    public CreateMessageStepdefs(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort,
                                 ConversationPersistencePort conversationPersistencePort,
                                 SplitRessourcePersistencePort splitRessourcePersistencePort, MessagePersistencePort messagePersistencePort,
                                 LanguageModelGateway languageModelGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        this.createMessageUseCase = new CreateMessageUseCase(authenticationGateway, projectPersistencePort, conversationPersistencePort, splitRessourcePersistencePort, messagePersistencePort, languageModelGateway, originalRessourcePersistencePort);
        this.projectPersistencePort = projectPersistencePort;
        this.conversationPersistencePort = conversationPersistencePort;
        this.authenticationGateway = authenticationGateway;
    }

    @When("I create a new conversation with first message {string} in project with ID {string}")
    public void iCreateANewConversationWithFirstMessageInProjectWithID(String firstMessage, String idProject) throws Exception {
        try {
            List<Message> execute = this.createMessageUseCase.execute(new CreateMessageUseCase.CreateMessageRequest(firstMessage, null, UUID.fromString(idProject)));

            assertNotNull(execute);
            assertTrue(execute.size() == 2);
            assertTrue(execute.get(0).getText().equals(firstMessage));
            assertTrue(execute.get(0).getType().equals(MessageSenderType.USER));

            assertNotNull(execute.get(0).getConversation() );
            assertNotNull(execute.get(0).getConversation().getId());

        } catch (Exception e) {
            this.messageFailed = true;
        }


    }


    @Then("the conversation should be created successfully with title {string} 25 first charactere of first message in project with ID {string}")
    public void theConversationShouldBeCreatedSuccessfullyWithTitleFirstCharactereOfFirstMessageInProjectWithID(String title, String projectId) {
        User user = this.authenticationGateway.currentUser().get();
        Optional<Project> byId = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(projectId), user.getId());

        assertTrue(byId.isPresent());
        assertTrue(byId.get().getConversations().size() == 1);
        assertTrue(byId.get().getConversations().get(0).getTitle().equals(title + "..."));

    }


    @Then("the creation should fail due to missing GitHub linkage in project with ID {string}")
    public void theCreationShouldFailDueToMissingGitHubLinkageInProjectWithID(String idProject) {
        User user = this.authenticationGateway.currentUser().get();
        assertTrue(this.messageFailed);
        Project project = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));

        assertNotNull(project);
        assertTrue(project.getConversations().size() == 0);

        this.messageFailed = false;
    }



    @When("I send a message with content {string} in the conversation in the project with ID {string}")
    public void iSendAMessageWithContentInTheConversationInTheProjectWithID(String message, String idProject) throws Exception {
        try {
            List<Message> messageList = this.createMessageUseCase.execute(new CreateMessageUseCase.CreateMessageRequest(
                    message,
                    null,
                    UUID.fromString(idProject)
            ));

            assertTrue(messageList.stream().anyMatch(m -> m.getText().equals(message)));
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }


    @Then("the system should perform an embedding on the message in the conversation in the project with ID {string}")
    public void theSystemShouldPerformAnEmbeddingOnTheMessageInTheConversationInTheProjectWithID(String projectId) {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
        List<Conversation> conversationList = this.conversationPersistencePort.findByProjectIdAndUserId(UUID.fromString(projectId), user.getId());
        assertFalse(conversationList.isEmpty());
        assertTrue(conversationList.size() == 1);
        assertTrue(conversationList.get(0).getMessages().size() == 2);
    }


    @And("enrich the message with retrieved resources to form {string}")
    public void enrichTheMessageWithRetrievedResourcesToForm(String resource) {
        String[] split = resource.split(";");
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));


        List<Project> projects = this.projectPersistencePort.findByUserId(user.getId());

        assertTrue(projects.size() == 1);

        Project project = projects.get(0);

        List<Conversation> conversations = this.conversationPersistencePort.findByProjectIdAndUserId(project.getId(), user.getId());

        assertTrue(conversations.size() == 1);

        Conversation conversation = conversations.get(0);

        List<Message> messages = conversation.getMessages();

        assertTrue(messages.size() == 2);

        // check if the enriched message in messges de type UserMessage contains the text enriched message, need filter on UserMessage
        Optional<Message> messageUserOpt = messages.stream().filter(message -> message instanceof UserMessage).findFirst();
        assertTrue(messageUserOpt.isPresent());
        UserMessage messageUser = (UserMessage) messageUserOpt.get();

        assertTrue(Arrays.stream(split).anyMatch(s -> ((UserMessage) messageUser).getEnrichedText().contains(s)));


    }

    @And("check if ChatGPT answer")
    public void sendTheEnrichedMessageToChatGPT() {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));


        List<Project> projects = this.projectPersistencePort.findByUserId(user.getId());

        assertTrue(projects.size() == 1);

        Project project = projects.get(0);

        List<Conversation> conversations = this.conversationPersistencePort.findByProjectIdAndUserId(project.getId(), user.getId());

        assertTrue(conversations.size() == 1);

        Conversation conversation = conversations.get(0);

        List<Message> messages = conversation.getMessages();

        assertTrue(messages.size() == 2);

        // check if You have AssistantMessage in messages
        assertTrue(messages.stream().anyMatch(message -> message instanceof AssistantMessage));

    }


    @Then("the message remains unchanged as {string}")
    public void theMessageRemainsUnchangedAs(String originalMessage) {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));


        List<Project> projects = this.projectPersistencePort.findByUserId(user.getId());

        assertTrue(projects.size() == 1);

        Project project = projects.get(0);

        List<Conversation> conversations = this.conversationPersistencePort.findByProjectIdAndUserId(project.getId(), user.getId());

        assertTrue(conversations.size() == 1);

        Conversation conversation = conversations.get(0);

        List<Message> messages = conversation.getMessages();

        assertTrue(messages.size() == 2);

        UserMessage userMessage = (UserMessage) messages.stream().filter(message -> message instanceof UserMessage).findFirst().get();

        assertTrue(userMessage.getEnrichedText().equals(originalMessage));

    }


    @Then("No conversation created")
    public void noConversationCreated() {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));


        List<Project> projects = this.projectPersistencePort.findByUserId(user.getId());

        assertTrue(projects.size() == 1);

        Project project = projects.get(0);

        List<Conversation> conversations = this.conversationPersistencePort.findByProjectIdAndUserId(project.getId(), user.getId());

        assertTrue(conversations.isEmpty());
    }

}
