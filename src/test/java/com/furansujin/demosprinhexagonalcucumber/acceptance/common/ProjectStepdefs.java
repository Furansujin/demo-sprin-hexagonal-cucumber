package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.CreateMessageUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.CreateProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.InviteUserInProjectUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectStepdefs {

    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;
    private final CreateProjectUseCase createProjectUseCase;
    private final InviteUserInProjectUseCase inviteUserInProjectUseCase;
    private final UserPersistencePort userPersistencePort;
    private boolean messageFailed;
    private final CreateMessageUseCase createMessageUseCase;


    public ProjectStepdefs(ProjectPersistencePort projectPersistencePort, AuthenticationGateway authenticationGateway,
                           UserPersistencePort userPersistencePort,
                           SplitRessourcePersistencePort splitRessourcePersistencePort, MessagePersistencePort messagePersistencePort,
                           ConversationPersistencePort conversationPersistencePort,  LanguageModelGateway languageModelGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        this.projectPersistencePort = projectPersistencePort;
        this.authenticationGateway = authenticationGateway;
        this.userPersistencePort = userPersistencePort;
        this.createMessageUseCase = new CreateMessageUseCase(authenticationGateway, projectPersistencePort,
                conversationPersistencePort, splitRessourcePersistencePort,
                messagePersistencePort, languageModelGateway, originalRessourcePersistencePort);
        this.createProjectUseCase = new CreateProjectUseCase(authenticationGateway, projectPersistencePort);
        this.inviteUserInProjectUseCase = new InviteUserInProjectUseCase(authenticationGateway, projectPersistencePort, userPersistencePort);

    }





    @And("the project with ID {string} has been linked to a GitHub repository")
    public void theProjectWithIDHasBeenLinkedToAGitHubRepository(String idProject) {
        User user = this.authenticationGateway.currentUser().get();

        assertTrue(user != null);
        Project project = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setRessources(new ArrayList<>());

//        OriginalRessource originalRessource = new OriginalRessource("text", "name", SourceType.GITHUB, FileType.JAVA);
//        project.getRessources().add(originalRessource);
        Project save = projectPersistencePort.save(project);
        assertTrue(save != null);
        assertTrue(save.getRessources().size() == 1);
    }



    @And("project exist with id {string} and userId {string} have role {string}")
    public void projectExistWithIdAndUserIdIsAdmin(String projectId, String userId, String role) {
        Optional<User> byId = this.userPersistencePort.findById(UUID.fromString(userId));
        assertTrue(byId.isPresent());
        ProjectRole projectRole = ProjectRole.valueOf(role);

        Project project = new Project();
        project.setId(UUID.fromString(projectId));
        project.setName("Project 1");
        project.setDescription("Description 1");
        List<UserProjectRole> userProjectRoles = new ArrayList<>();
        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setId(UUID.randomUUID());
        userProjectRole.setRole(projectRole);
        userProjectRole.setUser(byId.get());
        userProjectRoles.add(userProjectRole);
        project.setUserProjectRoles(userProjectRoles);
        Project save = projectPersistencePort.save(project);
        assertTrue(save != null);
        assertTrue(save.getUserProjectRoles().size() == 1);
        assertTrue(save.getUserProjectRoles().get(0).getRole().equals(projectRole));

    }

    @Given("I am an admin of the project with ID {string}")
    public void iAmAnAdminOfTheProjectWithID(String projectId) {
        User user = this.authenticationGateway.currentUser().get();

        assertTrue(user != null);
        Project project = new Project();
        project.setId(UUID.fromString(projectId));
        project.setName("Project 1");
        project.setDescription("Description 1");
        List<UserProjectRole> userProjectRoles = new ArrayList<>();
        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setId(UUID.randomUUID());
        userProjectRole.setRole(ProjectRole.ADMIN);
        userProjectRole.setUser(user);
        userProjectRoles.add(userProjectRole);
        project.setUserProjectRoles(userProjectRoles);
        Project save = projectPersistencePort.save(project);
        assertTrue(save != null);
        assertTrue(save.getUserProjectRoles().size() == 1);
        assertTrue(save.getUserProjectRoles().get(0).getRole().equals(ProjectRole.ADMIN));
    }





    @Given("I am the admin of the project with ID {string}")
    public void iAmTheAdminOfTheProjectWithID(String idProject) {
        User user = this.authenticationGateway.currentUser().get();
        assertTrue(user != null);
        Project project = new Project();
        project.setId(UUID.fromString(idProject));
        project.setName("Project 1");
        project.setDescription("Description 1");
        List<UserProjectRole> userProjectRoles = new ArrayList<>();
        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setId(UUID.randomUUID());
        userProjectRole.setRole(ProjectRole.ADMIN);
        userProjectRole.setUser(user);
        userProjectRoles.add(userProjectRole);
        project.setUserProjectRoles(userProjectRoles);

        Project save = projectPersistencePort.save(project);

        Optional<Project> projectFind = projectPersistencePort.findByIdAndUserId(project.getId(), user.getId());

        assertTrue(projectFind.isPresent());
        assertTrue(projectFind.get().getUserProjectRoles().size() == 1);
        assertTrue(projectFind.get().getUserProjectRoles().get(0).getRole().equals(ProjectRole.ADMIN));

    }



    @And("the project with ID {string} has not been linked to a GitHub repository")
    public void theProjectWithIDHasNotBeenLinkedToAGitHubRepository(String idProject) {
        User user = this.authenticationGateway.currentUser().get();
        Project project = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));

        assertNotNull(project);
        assertTrue(project.getRessources().size() == 0);
    }


    @But("the user with ID {string} has no projects")
    public void theUserWithIDHasNoProjects(String arg0) {
        User user = this.authenticationGateway.currentUser().get();
        List<Project> projects = this.projectPersistencePort.findByUserId(user.getId());
        assertTrue(projects.size() == 0);
    }

    @And("the user does not have access to the project with ID {string}")
    public void theUserDoesNotHaveAccessToTheProjectWithID(String idProject) {
        Project project = new Project();
        project.setId(UUID.fromString(idProject));
        project.setName("Project 1");
        project.setDescription("Description 1");

        Project save = projectPersistencePort.save(project);
    }

    @And("the user with ID {string} is not logged in")
    public void theUserWithIDIsNotLoggedIn(String idUser) {

        assertTrue( this.authenticationGateway.currentUser().isEmpty());
    }


}
