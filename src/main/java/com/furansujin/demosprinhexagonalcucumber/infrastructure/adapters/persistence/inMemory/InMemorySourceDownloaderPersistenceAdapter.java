package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class InMemorySourceDownloaderPersistenceAdapter implements SourceDownloaderPersistencePort {

    private Set<SourceDownloaderProcess> sourceDownloaderProcesses = new HashSet<>();
    @Override
    public SourceDownloaderProcess save(SourceDownloaderProcess sourceDownloader) {
        if(this.sourceDownloaderProcesses.contains(sourceDownloader))
            this.sourceDownloaderProcesses.remove(sourceDownloader);
       this.sourceDownloaderProcesses.add(sourceDownloader);
        return sourceDownloader;
    }

    @Override
    public Page<SourceDownloaderProcess> findByStateDownloaderProcess(StateDownloaderProcess stateDownloaderProcess, PageRequest pageRequest) {
        List<SourceDownloaderProcess> list = this.sourceDownloaderProcesses.stream().filter(s -> s.getStateDownloader().equals(stateDownloaderProcess)).toList().stream().skip(pageRequest.getOffset()).limit(pageRequest.getPageSize()).toList();
        return new PageImpl<>(list, pageRequest, list.size());
    }

    @Override
    public Page<SourceDownloaderProcess> findAll(PageRequest pageRequest) {
        List<SourceDownloaderProcess> list = this.sourceDownloaderProcesses.stream().skip(pageRequest.getOffset()).limit(pageRequest.getPageSize()).toList();
        return new PageImpl<>(list, pageRequest, list.size());
    }

}
