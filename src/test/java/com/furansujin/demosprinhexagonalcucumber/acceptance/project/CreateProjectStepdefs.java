package com.furansujin.demosprinhexagonalcucumber.acceptance.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.CreateProjectUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateProjectStepdefs {


private final CreateProjectUseCase createProjectUseCase;

private final ProjectPersistencePort projectPersistencePort;

private final AuthenticationGateway authenticationGateway;

    public CreateProjectStepdefs(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        this.createProjectUseCase = new CreateProjectUseCase(authenticationGateway, projectPersistencePort);
        this.projectPersistencePort = projectPersistencePort;
        this.authenticationGateway = authenticationGateway;
    }

    @When("I create a project with name {string} and description {string}")
    public void iCreateAProjectWithNameAndDescription(String nameProject, String descriptionProject) throws Exception {

        Project executed = this.createProjectUseCase.execute(new CreateProjectUseCase.CreateProjectRequest(nameProject, descriptionProject));

        assertNotNull(executed);
        assertNotNull(executed.getId());
        assertEquals(executed.getName(), nameProject);
        assertEquals(executed.getDescription(), descriptionProject);
    }

    @Then("the project should be created successfully with name {string} and description {string}")
    public void theProjectShouldBeCreatedSuccessfullyWithNameAndDescription(String nameProject, String descriptionProject) {
        User user = this.authenticationGateway.currentUser().get();
        List<Project> all = this.projectPersistencePort.findByUserId(user.getId());

        assertEquals(1, all.size());
        assertEquals(all.get(0).getName(), nameProject);
        assertEquals(all.get(0).getDescription(), descriptionProject);
    }

    @And("I should be assigned the admin role in project for user with ID {string}")
    public void iShouldBeAssignedTheAdminRoleInProjectForUserWithID(String idUser) {

        List<Project> projects = this.projectPersistencePort.findByUserId(UUID.fromString(idUser));

        assertTrue(projects.size() == 1);
        assertTrue(projects.get(0).getUserProjectRoles().size() == 1);
        assertTrue(projects.get(0).getUserProjectRoles().get(0).getRole().equals(ProjectRole.ADMIN));

    }
}
