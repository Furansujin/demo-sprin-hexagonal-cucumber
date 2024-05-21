package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.OriginalResourceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.OriginalResourceEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OriginalRessourcePersistencePostgreSQLAdapter implements OriginalRessourcePersistencePort {

    private final OriginalResourceEntityRepository originalResourceEntityRepository;

    @Override
    public OriginalRessource save(OriginalRessource originalRessource) {
        OriginalResourceEntity save = this.originalResourceEntityRepository.save(OriginalResourceEntity.fromDomain(originalRessource));
        return OriginalResourceEntity.toDomain(save);
    }

    @Override
    public Page<OriginalRessource> findByStateProcessSource(StateProcessSource stateProcessSource, Pageable pageable) {
        Page<OriginalResourceEntity> originalResourceEntities = this.originalResourceEntityRepository.findByStateProcessSource(stateProcessSource, pageable);
        return new PageImpl<>(OriginalResourceEntity.listToDomain(originalResourceEntities.getContent()), pageable, originalResourceEntities.getTotalElements());
    }

    @Override
    public List<OriginalRessource> saveAll(List<OriginalRessource> originalRessources) {
        List<OriginalResourceEntity> originalResourceEntities = OriginalResourceEntity.listFromDomain(originalRessources);
        return OriginalResourceEntity.listToDomain(this.originalResourceEntityRepository.saveAll(originalResourceEntities));
    }

    public boolean existsByProjectId(UUID projectId) {
        return this.originalResourceEntityRepository.existsByProjectId(projectId);
    }
}
