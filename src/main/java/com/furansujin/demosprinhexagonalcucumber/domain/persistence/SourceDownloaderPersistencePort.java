package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface SourceDownloaderPersistencePort {
    SourceDownloaderProcess save(SourceDownloaderProcess sourceDownloader);

    Page<SourceDownloaderProcess> findByStateDownloaderProcess(StateDownloaderProcess stateDownloaderProcess, PageRequest pageRequest);
    Page<SourceDownloaderProcess> findAll( PageRequest pageRequest);
}