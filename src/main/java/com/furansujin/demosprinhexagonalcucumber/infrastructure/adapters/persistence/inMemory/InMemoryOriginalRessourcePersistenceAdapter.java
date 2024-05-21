package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class InMemoryOriginalRessourcePersistenceAdapter implements OriginalRessourcePersistencePort {

    Set<OriginalRessource> originalRessources = new HashSet<>();
    @Override
    public OriginalRessource save(OriginalRessource originalRessource) {
        this.originalRessources.add(originalRessource);
        return originalRessource;
    }

    @Override
    public Page<OriginalRessource> findByStateProcessSource(StateProcessSource stateProcessSource, Pageable pageable) {

List<OriginalRessource> list = this.originalRessources.stream().filter(s -> s.getStateProcessSource().equals(stateProcessSource)).toList().stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();

        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public List<OriginalRessource> saveAll(List<OriginalRessource> originalRessources) {
        this.originalRessources.addAll(originalRessources);
        return this.originalRessources.stream().toList();
    }

    @Override
    public boolean existsByProjectId(UUID projectId) {
        return this.originalRessources.stream().anyMatch(s -> s.getProjectId().equals(projectId));
    }

    public  Set<OriginalRessource> findAll() {
        return this.originalRessources;
    }
}
