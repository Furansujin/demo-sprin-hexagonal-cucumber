package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseArgumentless;

import java.util.List;

public class FetchReposGithubUseCase implements UseCaseArgumentless< List<GithubRepo>> {


    private final GithubGateway githubGateway;
    private final AuthenticationGateway authenticationGateway;
    private final SourcePersistencePort sourcePersistencePort;

    public FetchReposGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort) {
        this.githubGateway = githubGateway;
        this.authenticationGateway = authenticationGateway;
        this.sourcePersistencePort = sourcePersistencePort;
    }

    @Override
    public List<GithubRepo> execute() throws Exception {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new Exception("User not logged in"));
        Source source = this.sourcePersistencePort.findFirstByUserIdAndType(user.getId(), SourceType.GITHUB)
                .orElseThrow(() -> new Exception("No github source found for user"));
        return this.githubGateway.fetchUserRepositories(user, source);
    }
}
