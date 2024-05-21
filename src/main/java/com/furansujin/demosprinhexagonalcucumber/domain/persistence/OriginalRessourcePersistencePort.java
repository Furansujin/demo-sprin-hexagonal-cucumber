package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OriginalRessourcePersistencePort {
    OriginalRessource save(OriginalRessource originalRessource);

    Page<OriginalRessource> findByStateProcessSource(StateProcessSource stateProcessSource, Pageable pageable);

    List<OriginalRessource> saveAll(List<OriginalRessource> batch);

    boolean existsByProjectId(UUID projectId);
}
