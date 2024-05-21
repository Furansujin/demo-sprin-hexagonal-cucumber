package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryProjectPersistenceAdapter;
import io.cucumber.java.en.And;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class ConversationStepdefs {

    private final ConversationPersistencePort conversationPersistencePort;

    private final UserPersistencePort userPersistencePort;

    private final InMemoryProjectPersistenceAdapter projectPersistencePort;

    public ConversationStepdefs(ConversationPersistencePort conversationPersistencePort, UserPersistencePort userPersistencePort, ProjectPersistencePort projectPersistencePort) {
        this.conversationPersistencePort = conversationPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.projectPersistencePort = (InMemoryProjectPersistenceAdapter) projectPersistencePort;
    }


    @And("conversation exist with {string} and project id {string} and userId {string}")
    public void conversationExistWithAndProjectIdAndUserId(String idConversation, String idProject, String idUser) {
        Optional<User> byId = this.userPersistencePort.findById(UUID.fromString(idUser));
        Optional<Project> byId1 = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), byId.get().getId());
        assertTrue(byId.isPresent());
        assertTrue(byId1.isPresent());
        this.conversationPersistencePort.save(Conversation.builder().id(UUID.fromString(idConversation)).project(byId1.get()).user(byId.get()).build());
    }

    @And("conversation exist with {string} and project id {string}")
    public void conversationExistWithAndProjectId(String idConversation, String idProject) {
        Optional<Project> byId1 = this.projectPersistencePort.findById(UUID.fromString(idProject));
        assertTrue(byId1.isPresent());
        this.conversationPersistencePort.save(Conversation.builder().id(UUID.fromString(idConversation)).project(byId1.get()).build());
    }
}
