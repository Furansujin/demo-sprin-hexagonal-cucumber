package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source_downloader_processes")
public class SourceDownloaderProcessEntity {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "source_id")
    private UUID sourceId;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "relative_path", columnDefinition = "TEXT")
    private String relativePath;

    @Enumerated(EnumType.STRING)
    private StateDownloaderProcess stateDownloader;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    private LocalDateTime createDate;

    private LocalDateTime endDate;

    private LocalDateTime lastUpdateDate;


    public static SourceDownloaderProcessEntity fromDomain(SourceDownloaderProcess sourceDownloaderProcess) {
        return SourceDownloaderProcessEntity.builder()
                .id(sourceDownloaderProcess.getId())
                .userId(sourceDownloaderProcess.getUserId())
                .sourceId(sourceDownloaderProcess.getSourceId())
                .projectId(sourceDownloaderProcess.getProjectId())
                .relativePath(sourceDownloaderProcess.getRelativePath())
                .stateDownloader(sourceDownloaderProcess.getStateDownloader())
                .errorMessage(sourceDownloaderProcess.getErrorMessage())
                .createDate(sourceDownloaderProcess.getCreateDate())
                .endDate(sourceDownloaderProcess.getEndDate())
                .lastUpdateDate(sourceDownloaderProcess.getLastUpdateDate())
                .build();
    }

    public static SourceDownloaderProcess toDomain(SourceDownloaderProcessEntity sourceDownloaderProcessEntity) {
        return SourceDownloaderProcess.builder()
                .id(sourceDownloaderProcessEntity.getId())
                .userId(sourceDownloaderProcessEntity.getUserId())
                .sourceId(sourceDownloaderProcessEntity.getSourceId())
                .projectId(sourceDownloaderProcessEntity.getProjectId())
                .relativePath(sourceDownloaderProcessEntity.getRelativePath())
                .stateDownloader(sourceDownloaderProcessEntity.getStateDownloader())
                .errorMessage(sourceDownloaderProcessEntity.getErrorMessage())
                .createDate(sourceDownloaderProcessEntity.getCreateDate())
                .endDate(sourceDownloaderProcessEntity.getEndDate())
                .lastUpdateDate(sourceDownloaderProcessEntity.getLastUpdateDate())
                .build();
    }

    public static List<SourceDownloaderProcess> listToDomain(List<SourceDownloaderProcessEntity> sourceDownloaderProcessEntities) {
        return sourceDownloaderProcessEntities.stream().map(SourceDownloaderProcessEntity::toDomain).toList();
    }

    public static List<SourceDownloaderProcessEntity> listFromDomain(List<SourceDownloaderProcess> sourceDownloaderProcesses) {
        return sourceDownloaderProcesses.stream().map(SourceDownloaderProcessEntity::fromDomain).toList();
    }

}
