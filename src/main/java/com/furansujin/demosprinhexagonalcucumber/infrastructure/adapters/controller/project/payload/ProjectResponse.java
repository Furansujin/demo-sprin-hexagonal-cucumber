package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.payload.UserProjectRolerResponse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProjectResponse {

    private UUID id;

    private String name;

    private String description;

    private List<UserProjectRolerResponse> userProjectRoles;
    public static ProjectResponse fromDomain(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .userProjectRoles(UserProjectRolerResponse.fromDomain(project.getUserProjectRoles()))
                .build();

    }

    public static List<ProjectResponse> fromDomain(List<Project> projects) {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(fromDomain(project));
        }
        return projectResponses;
    }
}
