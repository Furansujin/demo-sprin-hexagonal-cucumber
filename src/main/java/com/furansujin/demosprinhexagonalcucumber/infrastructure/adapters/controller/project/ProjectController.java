package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.CreateProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.GetAllProjectUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.InviteUserInProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload.ProjectResponse;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload.CreateProjectRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload.inviteUserInProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {


    private final CreateProjectUseCase createProjectUseCase ;
    private final GetAllProjectUserUseCase getAllProjectUserUseCase ;
    private final InviteUserInProjectUseCase inviteUserInProjectUseCase ;



    @PostMapping("/create")
    public ProjectResponse createProject(@RequestBody CreateProjectRequest createProjectRequest) throws Exception {
        return ProjectResponse.fromDomain(createProjectUseCase.execute(new CreateProjectUseCase.CreateProjectRequest(createProjectRequest.getName(),createProjectRequest.getDescription())));
    }

    @PostMapping("/invite")
    public ProjectResponse inviteUserInProject(@RequestBody inviteUserInProjectRequest inviteUserInProjectRequest) throws Exception {
        return ProjectResponse.fromDomain(inviteUserInProjectUseCase.execute(new InviteUserInProjectUseCase.InviteUserInProjectRequest(inviteUserInProjectRequest.getEmailUser(),inviteUserInProjectRequest.getIdProject())));
    }

    @PostMapping("/all")
    public List<ProjectResponse> getAllProjectUser() throws Exception {
        return ProjectResponse.fromDomain(getAllProjectUserUseCase.execute());
    }


}
