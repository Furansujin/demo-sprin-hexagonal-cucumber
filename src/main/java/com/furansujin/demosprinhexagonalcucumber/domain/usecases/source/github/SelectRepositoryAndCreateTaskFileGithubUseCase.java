package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.UserSourceNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseVoid;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SelectRepositoryAndCreateTaskFileGithubUseCase implements UseCaseVoid<SelectRepositoryAndCreateTaskFileGithubUseCase.SelectRepositoryGithubRequest> {



    private final GithubGateway githubGateway;
    private final AuthenticationGateway authenticationGateway;
    private final SourcePersistencePort sourcePersistencePort;
    private final SourceDownloaderPersistencePort sourceDownloaderPersistencePort;


    public SelectRepositoryAndCreateTaskFileGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort, SourceDownloaderPersistencePort sourceDownloaderPersistencePort){
        this.githubGateway = githubGateway;
        this.authenticationGateway = authenticationGateway;
        this.sourcePersistencePort = sourcePersistencePort;
        this.sourceDownloaderPersistencePort = sourceDownloaderPersistencePort;
    }


    @Override
    public void execute(SelectRepositoryGithubRequest arg)  {
           User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        Source source = this.sourcePersistencePort.findFirstByUserIdAndType(user.getId(), SourceType.GITHUB)
                .orElseThrow(() -> new UserSourceNotFoundException(SourceType.GITHUB, user.getId()));

        SourceDownloaderProcess sourceDownloader = new SourceDownloaderProcess(user.getId(), source.getId(), arg.projectId, arg.nameRepo);

       this.sourceDownloaderPersistencePort.save(sourceDownloader);

    }



    public record SelectRepositoryGithubRequest(String nameRepo, UUID projectId) {

    }
}
