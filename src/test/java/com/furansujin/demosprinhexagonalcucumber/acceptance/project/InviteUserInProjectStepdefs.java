package com.furansujin.demosprinhexagonalcucumber.acceptance.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.InviteUserInProjectUseCase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InviteUserInProjectStepdefs {


    private final InviteUserInProjectUseCase inviteUserInProjectUseCase;
    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;

    public InviteUserInProjectStepdefs(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort, UserPersistencePort userPersistencePort) {
        this.inviteUserInProjectUseCase = new InviteUserInProjectUseCase(authenticationGateway, projectPersistencePort, userPersistencePort);
        this.projectPersistencePort = projectPersistencePort;
        this.authenticationGateway = authenticationGateway;
    }

    private boolean messageFailed = false;

    @When("I invite a user with email {string} to the project with ID {string}")
    public void iInviteAUserWithEmailToTheProjectWithID(String emailUser, String idProject) {

        Project project = this.inviteUserInProjectUseCase.execute(new InviteUserInProjectUseCase.InviteUserInProjectRequest(emailUser, UUID.fromString(idProject)));

        assertNotNull(project);
        assertEquals(2, project.getUserProjectRoles().size());
        assertTrue(project.getUserProjectRoles().stream().anyMatch(userProjectRole -> userProjectRole.getUser().getUsername().equals(emailUser)));
        assertEquals(project.getUserProjectRoles().stream().filter(userProjectRole -> userProjectRole.getUser().getUsername().equals(emailUser)).findFirst().get().getRole(), ProjectRole.MEMBER);

    }


    @Then("the user with email {string} should be invited to the project with ID {string}")
    public void theUserWithEmailShouldBeInvitedToTheProjectWithID(String emailUser, String idProject) {
        User user = this.authenticationGateway.currentUser().get();
        Project project = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));

        assertNotNull(project);
        assertEquals(2, project.getUserProjectRoles().size());
        assertTrue(project.getUserProjectRoles().stream().anyMatch(userProjectRole -> userProjectRole.getUser().getUsername().equals(emailUser)));

    }


    @When("I invite a non-existent user with email {string} to the project  with ID {string}")
    public void iInviteANonExistentUserWithEmailToTheProjectWithID(String email, String idProject) {
        try {

            Project project = this.inviteUserInProjectUseCase.execute(new InviteUserInProjectUseCase.InviteUserInProjectRequest(email, UUID.fromString(idProject)));

            assertTrue(project == null);

        } catch (Exception e) {
            this.messageFailed = true;
            assertTrue(e.getMessage().equals("User not found"));
        }
    }


    @Then("the invitation should fail due to an invalid email address in the project  with ID {string}")
    public void theInvitationShouldFailDueToAnInvalidEmailAddressInTheProjectWithID(String idproject) {
        User user = this.authenticationGateway.currentUser().get();
        assertTrue(this.messageFailed);
        Optional<Project> byId = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idproject), user.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().getUserProjectRoles().size() == 1);

        this.messageFailed = false;
    }
}
