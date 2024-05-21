package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SourceDownloaderProcess {

    private UUID id;
    private UUID userId;
    private UUID sourceId;
    private UUID projectId;

    private String relativePath;

    private StateDownloaderProcess stateDownloader;

    private String errorMessage;

    private LocalDateTime createDate;

    private LocalDateTime endDate;

    private LocalDateTime lastUpdateDate;

    public SourceDownloaderProcess(UUID userId, UUID sourceId, UUID projectId, String relativePath ) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.sourceId = sourceId;
        this.projectId = projectId;
        this.relativePath = relativePath;
        this.stateDownloader = StateDownloaderProcess.PENDING;
        this.errorMessage = null;
        this.createDate = LocalDateTime.now();
        this.endDate = null;
        this.lastUpdateDate = null;
    }


}
