package com.furansujin.demosprinhexagonalcucumber.domain.usecases.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;

import java.util.List;
import java.util.UUID;

public class CreateProjectUseCase implements UseCase<Project, CreateProjectUseCase.CreateProjectRequest> {


    private final AuthenticationGateway authenticationGateway;
    private final ProjectPersistencePort projectPersistencePort;

    public CreateProjectUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.projectPersistencePort = projectPersistencePort;
    }

    @Override
    public Project execute(CreateProjectUseCase.CreateProjectRequest request) throws Exception {
        User user = this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
        // create Role admin
        UserProjectRole userProjectRole = UserProjectRole.builder()
                .id(UUID.randomUUID())
                .role(ProjectRole.ADMIN)
                .user(user)
                .build();
        // create project
        Project project = Project.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .userProjectRoles(List.of(userProjectRole))
                .build();

       return this.projectPersistencePort.save(project);
    }

    public record CreateProjectRequest(String name, String description) {
    }
}
