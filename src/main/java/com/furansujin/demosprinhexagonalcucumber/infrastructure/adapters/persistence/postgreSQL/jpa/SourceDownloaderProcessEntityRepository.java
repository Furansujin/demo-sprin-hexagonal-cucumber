package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.SourceDownloaderProcessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SourceDownloaderProcessEntityRepository extends JpaRepository<SourceDownloaderProcessEntity, UUID> {
    Page<SourceDownloaderProcessEntity> findByStateDownloader(StateDownloaderProcess stateDownloaderProcess, PageRequest pageRequest);
}
