package com.furansujin.demosprinhexagonalcucumber.domain.persistence;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SourcePersistencePort {
    Set<Source> findByUserId(UUID currentUserId);

    Optional<Source> findFirstByUserIdAndType(UUID currentUserId, SourceType sourceTypeEnum);

    Source save(Source source);

    Optional<Source> findByIdAndUserId(UUID sourceId, UUID id);
}