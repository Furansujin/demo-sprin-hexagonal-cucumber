package com.furansujin.demosprinhexagonalcucumber.domain.usecases.project;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;

import java.util.UUID;


public class InviteUserInProjectUseCase implements UseCase<Project, InviteUserInProjectUseCase.InviteUserInProjectRequest> {


    private AuthenticationGateway authenticationGateway;
    private ProjectPersistencePort projectPersistencePort;
    private UserPersistencePort userPersistencePort;

    public InviteUserInProjectUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort, UserPersistencePort userPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.projectPersistencePort = projectPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    public Project execute(InviteUserInProjectRequest inviteUserInProjectRequest) {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
        User userInveted = this.userPersistencePort.findByUsername(inviteUserInProjectRequest.emailUser()).orElseThrow(() -> new RuntimeException("User not found"));

        Project project = this.projectPersistencePort.findByIdAndUserId(inviteUserInProjectRequest.idProject(), user.getId()).orElseThrow(() -> new RuntimeException("Project not found"));

        project.addMember(userInveted);

        return this.projectPersistencePort.save(project);

    }

    public record InviteUserInProjectRequest(String emailUser, UUID idProject) {

    }


}
