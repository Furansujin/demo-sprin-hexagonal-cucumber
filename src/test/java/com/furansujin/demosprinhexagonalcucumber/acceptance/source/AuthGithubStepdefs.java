package com.furansujin.demosprinhexagonalcucumber.acceptance.source;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.AuthGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.inMemory.InMemoryGithubAdapter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthGithubStepdefs {


    private final AuthenticationGateway authenticationGateway;

    private final ProjectPersistencePort projectPersistencePort;

    private final SourcePersistencePort sourcePersistencePort;

    private final InMemoryGithubAdapter githubGateway;

    private final AuthGithubUseCase authGithubUseCase;

    private String messageError;

    public AuthGithubStepdefs(AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort, ProjectPersistencePort projectPersistencePort, GithubGateway githubGateway) {
        this.authenticationGateway = authenticationGateway;
        this.projectPersistencePort = projectPersistencePort;
        this.githubGateway = (InMemoryGithubAdapter) githubGateway;
        this.sourcePersistencePort = sourcePersistencePort;
        this.authGithubUseCase = new AuthGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort);

        this.messageError = "";
    }







    @And("{string} is valid GitHub credentials")
    public void isValidGitHubCredentials(String codeValid) {
        this.githubGateway.addCode(codeValid);
    }

    @When("I attempt to authenticate with {string} invalid credentials")
    public void iAttemptToAuthenticateWithInvalidCredentials(String code) {
        try {
            this.authGithubUseCase.execute(new AuthGithubUseCase.AuthGithubRequest(code));
        } catch (Exception e) {
            this.messageError = e.getMessage();
        }
    }


    @Then("I should receive an error message {string}")
    public void iShouldReceiveAnErrorMessage(String arg0) {
        assertEquals(arg0, this.messageError);
    }

    @When("I authenticate and give a code {string} to the system in my Sources")
    public void iAuthenticateAndGiveATokenToTheSystemInMySources(String code) throws Exception {
        try {
            this.authGithubUseCase.execute(new AuthGithubUseCase.AuthGithubRequest(code));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Then("I should be have source github in my Sources with token")
    public void iShouldBeHaveSourceGithubInMySourcesWithToken() {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));

        Set<Source> byUserId = this.sourcePersistencePort.findByUserId(user.getId());
        assertEquals(1, byUserId.size());
        assertTrue(byUserId.stream().anyMatch(source -> source.getType().equals(SourceType.GITHUB)));
        assertTrue(byUserId.stream().filter(source -> source.getType().equals(SourceType.GITHUB)).noneMatch(source -> source.getAccessToken().isEmpty()));
    }


}
