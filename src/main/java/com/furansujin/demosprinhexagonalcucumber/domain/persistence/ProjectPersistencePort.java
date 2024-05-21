package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ProjectPersistencePort {
    Project save(Project project);
    List<Project> findByUserId(UUID userId);
    Optional<Project> findByIdAndUserId(UUID id, UUID userId);

}