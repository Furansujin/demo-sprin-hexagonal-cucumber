package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserProjectRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    private UUID id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserProjectRoleEntity> userProjectRoles = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OriginalResourceEntity> resources = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConversationEntity> conversations = new ArrayList<>();

    public static Project toDomain(ProjectEntity entity) {
        Project project = new Project();
        project.setId(entity.getId());
        project.setName(entity.getName());
        project.setDescription(entity.getDescription());
        project.setUserProjectRoles(UserProjectRoleEntity.listToDomain(entity.getUserProjectRoles()));
//        project.setRessources(OriginalResourceEntity.listToDomain(entity.getResources()));
//        project.setConversations(
//                entity.getConversations()
//                        .stream()
//                        .map(conversationEntity -> Conversation.builder().id(conversationEntity.getId()).build())
//                        .collect(Collectors.toList())
//        );
        return project;
    }
public static ProjectEntity fromDomain(Project project) {
    ProjectEntity entity = new ProjectEntity();
    entity.setId(project.getId());
    entity.setName(project.getName());
    entity.setDescription(project.getDescription());
//    entity.setResources(OriginalResourceEntity.listFromDomain(project.getRessources()));

    // Handle UserProjectRoles and Conversations without causing infinite loops
    if (project.getUserProjectRoles() != null) {
        for (UserProjectRole upr : project.getUserProjectRoles()) {
            UserProjectRoleEntity uprEntity = UserProjectRoleEntity.fromDomain(upr);
            uprEntity.setProject(entity); // Set project reference here
            entity.getUserProjectRoles().add(uprEntity);
        }
    }
    if (project.getConversations() != null) {
        for (Conversation conv : project.getConversations()) {
            ConversationEntity convEntity = ConversationEntity.builder().id(conv.getId()).build();
            entity.getConversations().add(convEntity);
        }
    }

    return entity;
}

}