package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.inMemory.InMemoryGithubAdapter;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GitHubGatewayStepDefs {


    private final InMemoryGithubAdapter githubGateway;
    private final SourcePersistencePort sourcePersistencePort;

    private final AuthenticationGateway authenticationGateway;

    public GitHubGatewayStepDefs(GithubGateway githubGateway , SourcePersistencePort sourcePersistencePort, AuthenticationGateway authenticationGateway) {
        this.githubGateway = (InMemoryGithubAdapter) githubGateway;
        this.sourcePersistencePort = sourcePersistencePort;
        this.authenticationGateway = authenticationGateway;
    }


    @And("I have source github with repository {string}")
    public void IHaveSourceGithubWithRepository(String listRepoName) {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
        Source source = new Source( SourceType.GITHUB, "1125555555511111111122222222211111", user.getId(), "miraisystems");
        Source save = this.sourcePersistencePort.save(source);
        String[] split = listRepoName.split(";");
        for (String s : split) {
            this.githubGateway.addRepo(new GithubRepo(s, "description" ));
        }
        assertNotNull(save);
        assertNotNull(save.getId());


    }
}
