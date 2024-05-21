package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.SourceEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.SourceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SourcePersistencePostgreSQLAdapter implements SourcePersistencePort {

    private final SourceEntityRepository sourceEntityRepository;

    @Override
    public Set<Source> findByUserId(UUID currentUserId) {
        Set<SourceEntity> sourceEntities =  this.sourceEntityRepository.findByUserId(currentUserId);
        return SourceEntity.listToDomain(sourceEntities);
    }

    @Override
    public Optional<Source> findFirstByUserIdAndType(UUID currentUserId, SourceType sourceTypeEnum) {
        Optional<SourceEntity> sourceEntity =  this.sourceEntityRepository.findFirstByUserIdAndType(currentUserId, sourceTypeEnum);
        return sourceEntity.map(SourceEntity::toDomain);
    }

    @Override
    public Source save(Source source) {
        SourceEntity sourceEntity =  this.sourceEntityRepository.save(SourceEntity.fromDomain(source));
        return SourceEntity.toDomain(sourceEntity);
    }

    @Override
    public Optional<Source> findByIdAndUserId(UUID sourceId, UUID id) {
        Optional<SourceEntity> sourceEntity = this.sourceEntityRepository.findByIdAndUserId(sourceId, id);
        return sourceEntity.map(SourceEntity::toDomain);
    }
}
