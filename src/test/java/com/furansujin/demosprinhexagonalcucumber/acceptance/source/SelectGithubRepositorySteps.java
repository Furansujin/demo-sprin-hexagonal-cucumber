package com.furansujin.demosprinhexagonalcucumber.acceptance.source;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.SelectRepositoryAndCreateTaskFileGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySourcePersistenceAdapter;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectGithubRepositorySteps {

    private final SelectRepositoryAndCreateTaskFileGithubUseCase selectRepositoryAndCreateTaskFileGithubUseCase;

    private final SourceDownloaderPersistencePort sourceDownloaderPersistencePort;
    private final InMemorySourcePersistenceAdapter sourcePersistencePort;
    private String messageError;

    public SelectGithubRepositorySteps(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort, SourceDownloaderPersistencePort sourceDownloaderPersistencePort) {
        this.selectRepositoryAndCreateTaskFileGithubUseCase = new SelectRepositoryAndCreateTaskFileGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort, sourceDownloaderPersistencePort);
        this.sourceDownloaderPersistencePort = sourceDownloaderPersistencePort;
        this.sourcePersistencePort = (InMemorySourcePersistenceAdapter) sourcePersistencePort;
        this.messageError = "";
    }


    @When("the user selects a GitHub repository named {string} for project with ID {string}")
    public void theUserSelectsAGitHubRepositoryNamedForProjectWithID(String arg0, String arg1) {
        try {
            this.selectRepositoryAndCreateTaskFileGithubUseCase.execute(new SelectRepositoryAndCreateTaskFileGithubUseCase.SelectRepositoryGithubRequest(arg0, UUID.fromString(arg1)));

        } catch (Exception e) {
            this.messageError = e.getMessage();
        }
    }

    @Then("a new source downloader should be created for {string}")
    public void aNewSourceDownloaderShouldBeCreatedFor(String arg0) {
        Page<SourceDownloaderProcess> all = this.sourceDownloaderPersistencePort.findAll(PageRequest.of(0, 10));
        assertEquals(1, all.getTotalElements());
    }

    @Given("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("an error {string} should be thrown")
    public void anErrorShouldBeThrown(String arg0) {
       assertTrue(this.messageError.contains(arg0));
    }

    @But("the user does not have a GitHub source type")
    public void theUserDoesNotHaveAGitHubSourceType() {
        this.sourcePersistencePort.deleteAll();
    }

    @When("there is an issue in creating a source downloader")
    public void thereIsAnIssueInCreatingASourceDownloader() {
        throw new io.cucumber.java.PendingException();
    }






    @Then("the system should create task download of all files from the repository")
    public void theSystemShouldCreateTaskDownloadOfAllFilesFromTheRepository() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<SourceDownloaderProcess> all = this.sourceDownloaderPersistencePort.findAll(pageRequest);
        assertEquals(1, all.getTotalElements());
    }
}
