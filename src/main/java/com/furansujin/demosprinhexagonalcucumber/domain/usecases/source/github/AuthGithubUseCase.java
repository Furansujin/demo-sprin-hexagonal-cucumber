package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubUser;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.ErrorGetTokenWithCodeException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseVoid;

import java.util.Optional;


public class AuthGithubUseCase implements UseCaseVoid<AuthGithubUseCase.AuthGithubRequest> {


    private final GithubGateway githubGateway;
    private final AuthenticationGateway authenticationGateway;
    private final SourcePersistencePort sourcePersistencePort;

    public AuthGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort) {
        this.githubGateway = githubGateway;
        this.authenticationGateway = authenticationGateway;
        this.sourcePersistencePort = sourcePersistencePort;
    }


    @Override
    public void execute(AuthGithubRequest arg)   {
           User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);

        Optional<String> accessToken = this.githubGateway.exchangeCodeForAccessToken(arg.code);
        if(accessToken.isPresent()) {
            Optional<GithubUser> githubUser = this.githubGateway.useAccessTokenForInformation(accessToken.get());
            if(githubUser.isEmpty()) {
                throw new ErrorGetTokenWithCodeException();
            }
            Source source = new Source( SourceType.GITHUB, accessToken.get(), user.getId(), githubUser.get().getLogin());
            this.sourcePersistencePort.save(source);

        } else {
            throw new ErrorGetTokenWithCodeException();
        }
    }

    public record AuthGithubRequest(String code) {

    }
}
