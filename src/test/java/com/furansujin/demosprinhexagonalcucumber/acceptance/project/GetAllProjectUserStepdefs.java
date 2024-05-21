package com.furansujin.demosprinhexagonalcucumber.acceptance.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.GetAllProjectUserUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetAllProjectUserStepdefs {


    private List<Project> projects;

    private final GetAllProjectUserUseCase getAllProjectUserUseCase;

    private final ProjectPersistencePort projectPersistencePort;

    private String messageError;


    public GetAllProjectUserStepdefs( AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        this.getAllProjectUserUseCase = new GetAllProjectUserUseCase(authenticationGateway, projectPersistencePort);
        this.projectPersistencePort = projectPersistencePort;
        this.messageError = "";
    }

    @When("the user requests to see all their projects")
    public void theUserRequestsToSeeAllTheirProjects() {
        try {
            projects = getAllProjectUserUseCase.execute();
        } catch (Exception e) {
            this.messageError = e.getMessage();
        }
    }

    @Then("all projects associated with user ID {string} should be returned")
    public void allProjectsAsssociatedWithUserIDShouldBeReturned(String userId) {

        assertNotNull(projects);
        assertTrue(!projects.isEmpty());
        assertTrue(projects.stream().anyMatch(p -> p.getUserProjectRoles().stream().anyMatch(upr -> upr.getUser().getId().equals(UUID.fromString(userId)))));
    }

    @Then("an error message {string} should be returned")
    public void anErrorMessageShouldBeReturned(String message) {
        assertTrue(this.messageError.contains(message));
    }

    @Then("an empty project list should be returned for user ID {string}")
    public void anEmptyProjectListShouldBeReturnedForUserID(String arg0) {
        assertNotNull(projects);
        assertTrue(projects.size() == 0);
    }


}
