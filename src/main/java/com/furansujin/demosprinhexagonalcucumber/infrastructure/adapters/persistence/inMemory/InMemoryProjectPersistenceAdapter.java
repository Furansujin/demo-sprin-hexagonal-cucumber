package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;


import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;



public class InMemoryProjectPersistenceAdapter implements ProjectPersistencePort {

    private Set<Project> projects = new HashSet<>();


 private final InMemoryConversationPersistenceAdapter conversationPersistencePort;

 private final SplitRessourcePersistencePort splitRessourcePersistencePort;

    public InMemoryProjectPersistenceAdapter(ConversationPersistencePort conversationPersistencePort, SplitRessourcePersistencePort splitRessourcePersistencePort) {
        this.conversationPersistencePort = (InMemoryConversationPersistenceAdapter) conversationPersistencePort;
        this.splitRessourcePersistencePort = splitRessourcePersistencePort;
    }

    @Override
    public Project save(Project project) {
        this.projects.add(project);
        return project;
    }

    @Override
    public List<Project> findByUserId(UUID userId) {
        return this.projects.stream()
                .filter(p -> p.getUserProjectRoles().stream().anyMatch(upr -> upr.getUser().getId().equals(userId)))
                .map(p -> {
                    p.setConversations(this.conversationPersistencePort.findByProjectIdAndUserId(p.getId(), userId));
                    return p;
                })
                .toList();
    }



    @Override
    public Optional<Project> findByIdAndUserId(UUID id, UUID userId) {
        return this.projects.stream().filter(p -> p.getId().equals(id) && p.getUserProjectRoles().stream().anyMatch(upr -> upr.getUser().getId().equals(userId)))
                .map(p -> {
                    p.setConversations(this.conversationPersistencePort.findByProjectIdAndUserId(p.getId(), userId));
                    return p;
                })
                .findFirst();
    }


    public Optional<Project> findById(UUID uuid) {
        return this.projects.stream().filter(p -> p.getId().equals(uuid))
                .map(p -> {
                    p.setConversations(this.conversationPersistencePort.findByProjectId(p.getId()));
                    return p;
                })
                .findFirst();
    }
}
