package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.ProjectEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.ProjectEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectPersistencePostgreSQLAdapter implements ProjectPersistencePort {

    private final ProjectEntityRepository projectEntityRepository;

    @Override
    public Project save(Project project) {
        ProjectEntity save = this.projectEntityRepository.save(ProjectEntity.fromDomain(project));
        return ProjectEntity.toDomain(save);
    }

    @Override
    public List<Project> findByUserId(UUID userId) {
        return this.projectEntityRepository.findByUserId(userId).stream()
                .map(ProjectEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Project> findByIdAndUserId(UUID id, UUID userId) {
        return Optional.of(ProjectEntity.toDomain(projectEntityRepository.findByIdAndUserId(id, userId)));
    }


}
