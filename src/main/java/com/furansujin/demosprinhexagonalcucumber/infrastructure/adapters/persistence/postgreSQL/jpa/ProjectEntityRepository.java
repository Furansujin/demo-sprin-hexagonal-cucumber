package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, UUID> {

    @Query("SELECT p FROM ProjectEntity p JOIN p.userProjectRoles upr WHERE upr.user.id = :userId")
    List<ProjectEntity> findByUserId(@Param("userId") UUID userId);


@Query("SELECT p FROM ProjectEntity p JOIN p.userProjectRoles upr WHERE p.id = :id AND upr.user.id = :userId")
    ProjectEntity findByIdAndUserId(UUID id, UUID userId);
}
