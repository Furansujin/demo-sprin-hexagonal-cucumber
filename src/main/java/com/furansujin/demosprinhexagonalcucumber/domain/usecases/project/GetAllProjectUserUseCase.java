package com.furansujin.demosprinhexagonalcucumber.domain.usecases.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseArgumentless;

import java.util.List;

public class GetAllProjectUserUseCase implements UseCaseArgumentless<List<Project>> {

    private final ProjectPersistencePort projectPersistencePort;
    private final AuthenticationGateway authenticationGateway;
    public GetAllProjectUserUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.projectPersistencePort = projectPersistencePort;
    }

    @Override
    public List<Project> execute() throws Exception {
        User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        return this.projectPersistencePort.findByUserId(user.getId());
    }
}
