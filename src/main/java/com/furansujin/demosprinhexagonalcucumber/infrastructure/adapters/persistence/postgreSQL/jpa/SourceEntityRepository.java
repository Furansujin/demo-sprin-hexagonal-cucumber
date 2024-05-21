package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SourceEntityRepository extends JpaRepository<SourceEntity, UUID> {
    Set<SourceEntity> findByUserId(UUID currentUserId);

    Optional<SourceEntity> findFirstByUserIdAndType(UUID currentUserId, SourceType sourceTypeEnum);

    Optional<SourceEntity> findByIdAndUserId(UUID sourceId, UUID id);
}
