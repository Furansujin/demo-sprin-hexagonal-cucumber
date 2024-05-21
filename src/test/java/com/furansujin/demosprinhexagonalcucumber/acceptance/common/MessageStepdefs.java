package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.CreateMessageUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.inMemory.InMemoryLanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryConversationPersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryMessagePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySplitRessourcePersistenceAdapter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageStepdefs {

    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;
    private final UserPersistencePort userPersistencePort;

    private final CreateMessageUseCase createMessageUseCase;
    private final InMemoryConversationPersistenceAdapter conversationPersistencePort;
    private final InMemoryMessagePersistenceAdapter messagePersistencePort;
    private final InMemoryLanguageModelGateway languageModelGateway;
    private final InMemorySplitRessourcePersistenceAdapter splitRessourcePersistencePort;

    public MessageStepdefs(ProjectPersistencePort projectPersistencePort, AuthenticationGateway authenticationGateway, UserPersistencePort userPersistencePort,
                           InMemorySplitRessourcePersistenceAdapter splitRessourcePersistencePort, MessagePersistencePort messagePersistencePort,
                           ConversationPersistencePort conversationPersistencePort, LanguageModelGateway languageModelGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {

        this.projectPersistencePort = projectPersistencePort;
        this.authenticationGateway = authenticationGateway;
        this.userPersistencePort = userPersistencePort;
        this.conversationPersistencePort = (InMemoryConversationPersistenceAdapter) conversationPersistencePort;
        this.messagePersistencePort = (InMemoryMessagePersistenceAdapter) messagePersistencePort;
        this.languageModelGateway =  (InMemoryLanguageModelGateway) languageModelGateway;
        this.splitRessourcePersistencePort = splitRessourcePersistencePort;

        this.createMessageUseCase = new CreateMessageUseCase(authenticationGateway, projectPersistencePort, conversationPersistencePort, splitRessourcePersistencePort, messagePersistencePort, languageModelGateway, originalRessourcePersistencePort);


    }
    @Given("the GitHub repository files for project with ID {string} have been split and vectorized")
    public void theGitHubRepositoryFilesForProjectWithIDHaveBeenSplitAndVectorized(String arg0) {
        throw new io.cucumber.java.PendingException();
    }
//    @And("I have permission to create conversation in the project with ID {string}")
//    public void iHavePermissionToCreateConversationInTheProjectWithID(String idProject) {
//        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
//
//        Project project = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));
//        if(project.getUserProjectRoles().stream().noneMatch(userProjectRole -> userProjectRole.getUser().getId().equals(user.getId()))) {
//            project.getUserProjectRoles().add(UserProjectRole.builder()
//                    .id(UUID.randomUUID())
//                    .user(user)
//                    .role(ProjectRole.MEMBER)
//                    .projectId(project.getId())
//                    .build());
//            this.projectPersistencePort.save(project);
//        }
//    }







    @And("no similar resources are found in the vector database")
    public void noSimilarResourcesAreFoundInTheVectorDatabase() {
        this.splitRessourcePersistencePort.setSplitRessources(new HashSet<>());
    }



    @Then("the system should not perform any embedding or enrichment")
    public void theSystemShouldNotPerformAnyEmbeddingOrEnrichment() {
        throw new io.cucumber.java.PendingException();
    }




    @And("In project with ID {string} you have {string}")
    public void inProjectWithIDYouHave(String projectId, String similarResources) {
        String[] split = similarResources.split(";");
        Set<SplitRessource> splitRessources = new HashSet<>();
        for (String s : split) {
            splitRessources.add(SplitRessource.builder()
                    .id(UUID.randomUUID())
                    .name("file.js")
                    .text(s)
                    .position(1)
                    .totalParts(1)
//                    .vector(new float[]{1, 2, 3}.toString())
                    .build());
        }
        this.splitRessourcePersistencePort.setSplitRessources(splitRessources);

        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));


        Optional<Project> projectOpt = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(projectId), user.getId());

        assertTrue(projectOpt.isPresent());

        Project project = projectOpt.get();
        List<OriginalRessource> ressources = new ArrayList<>();
//        for (String s : split) {
//            ressources.add(new OriginalRessource(s, "name", SourceType.GITHUB, FileType.JAVA));
//        }

        project.setRessources(ressources);
        this.projectPersistencePort.save(project);
    }


    @And("message exist with id {string} and conversation id {string}")
    public void messageExistWithIdAndConversationId(String idMessage, String idConversation) {
        Optional<Conversation> byId = this.conversationPersistencePort.findById(UUID.fromString(idConversation));
        assertTrue(byId.isPresent());
        this.messagePersistencePort.saveUserMessage(UserMessage.builder().id(UUID.fromString(idMessage)).originalText("test text").conversation(byId.get()).build());
    }
}
