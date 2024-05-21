package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.OriginalResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OriginalResourceEntityRepository extends JpaRepository<OriginalResourceEntity, UUID> {
    Page<OriginalResourceEntity> findByStateProcessSource(StateProcessSource stateProcessSource, Pageable pageable);

    boolean existsByProjectId(UUID projectId);
}
