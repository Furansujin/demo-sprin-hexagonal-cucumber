package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
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
public class Project   {

    private UUID id;

    private String name;

    private String description;

    private List<UserProjectRole> userProjectRoles = new ArrayList<>();

    private List<OriginalRessource> ressources = new ArrayList<>();

    private List<Conversation> conversations = new ArrayList<>();


    public void addMember(User userInveted) {
        UserProjectRole userProjectRole = UserProjectRole.builder()
                .id(UUID.randomUUID())
                .role(ProjectRole.MEMBER)
                .user(userInveted)
                .build();
        this.userProjectRoles.add(userProjectRole);
    }
}