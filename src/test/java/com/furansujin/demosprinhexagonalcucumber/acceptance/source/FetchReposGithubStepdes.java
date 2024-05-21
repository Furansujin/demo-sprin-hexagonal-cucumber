package com.furansujin.demosprinhexagonalcucumber.acceptance.source;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.FetchReposGithubUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FetchReposGithubStepdes {

    private final FetchReposGithubUseCase fetchReposGithubUseCase;

    private List<GithubRepo> githubRepos;

    public FetchReposGithubStepdes(GithubGateway githubGateway , SourcePersistencePort sourcePersistencePort, AuthenticationGateway authenticationGateway) {
        this.fetchReposGithubUseCase = new FetchReposGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort);
        this.githubRepos = null;
    }

    @When("fetching Repositories github")
    public void fetchingRepositoriesGithub() throws Exception {
        this.githubRepos = this.fetchReposGithubUseCase.execute();
        assertNotNull(this.githubRepos);

    }

    @And("I should see the repository {string} in the list")
    public void iShouldSeeTheRepositoryInTheList(String repoList) {
        String[] split = repoList.split(";");
        for (String s : split) {
            assertTrue(this.githubRepos.stream().anyMatch(repo -> repo.getName().equals(s)));
        }
    }



    @Then("I should to retrieve the list of my repositories")
    public void iShouldToRetrieveTheListOfMyRepositories() {
        assertTrue(this.githubRepos.size() > 0);
    }



}
