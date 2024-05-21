package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.SourceDownloaderProcessEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.SourceDownloaderProcessEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceDownloaderPersistencePostgreSQLAdapter implements SourceDownloaderPersistencePort {

    private final SourceDownloaderProcessEntityRepository sourceDownloaderProcessEntityRepository;

    @Override
    public SourceDownloaderProcess save(SourceDownloaderProcess sourceDownloader) {
        SourceDownloaderProcessEntity save =  this.sourceDownloaderProcessEntityRepository.save(SourceDownloaderProcessEntity.fromDomain(sourceDownloader));
        return SourceDownloaderProcessEntity.toDomain(save);
    }

    @Override
    public Page<SourceDownloaderProcess> findByStateDownloaderProcess(StateDownloaderProcess stateDownloaderProcess, PageRequest pageRequest) {
        Page<SourceDownloaderProcessEntity>  sourceDownloaderProcessEntity =  this.sourceDownloaderProcessEntityRepository.findByStateDownloader(stateDownloaderProcess, pageRequest);
        return new PageImpl<>(SourceDownloaderProcessEntity.listToDomain(sourceDownloaderProcessEntity.getContent()), pageRequest, sourceDownloaderProcessEntity.getTotalElements());
    }

    @Override
    public Page<SourceDownloaderProcess> findAll(PageRequest pageRequest) {
        Page<SourceDownloaderProcessEntity>  sourceDownloaderProcessEntity = this.sourceDownloaderProcessEntityRepository.findAll(pageRequest);
        return new PageImpl<>(SourceDownloaderProcessEntity.listToDomain(sourceDownloaderProcessEntity.getContent()), pageRequest, sourceDownloaderProcessEntity.getTotalElements());
    }
}
