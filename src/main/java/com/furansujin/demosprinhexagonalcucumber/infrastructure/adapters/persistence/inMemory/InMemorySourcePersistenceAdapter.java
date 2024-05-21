package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;


import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class InMemorySourcePersistenceAdapter implements SourcePersistencePort {

    private Set<Source> sources = new HashSet<>();
    @Override
    public Set<Source> findByUserId(UUID currentUserId) {
        return this.sources.stream().filter(s -> s.getUserId().equals(currentUserId)).collect(Collectors.toSet());
    }

    @Override
    public Optional<Source> findFirstByUserIdAndType(UUID currentUserId, SourceType sourceTypeEnum) {
        return Optional.ofNullable(this.sources.stream().filter(s -> s.getUserId().equals(currentUserId) && s.getType().equals(sourceTypeEnum)).findFirst().orElse(null));
    }

    @Override
    public Source save(Source source) {
           this.sources.add(source);
            return source;
    }

    @Override
    public Optional<Source> findByIdAndUserId(UUID sourceId, UUID id) {
        return this.sources.stream().filter(s -> s.getId().equals(sourceId) && s.getUserId().equals(id)).findFirst();
    }

    public void deleteAll() {
        this.sources.clear();
    }
}
