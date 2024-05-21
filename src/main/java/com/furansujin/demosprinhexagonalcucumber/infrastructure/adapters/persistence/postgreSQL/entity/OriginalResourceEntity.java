package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.FileType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "original_resources")
public class OriginalResourceEntity {

    @Id
    private UUID id;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    private String name;

    @Enumerated(EnumType.STRING)
    private SourceType type;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private StateProcessSource stateProcessSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private ProjectEntity project;


    public static OriginalRessource toDomain(OriginalResourceEntity entity) {

        return OriginalRessource.builder()
                .id(entity.getId())
                .projectId(entity.getProjectId())
                .text(entity.getText())
                .name(entity.getName())
                .type(entity.getType())
                .fileType(entity.getFileType())
                .createDate(entity.getCreateDate())
                .stateProcessSource(entity.getStateProcessSource())
                .build();
    }

    public static OriginalResourceEntity fromDomain(OriginalRessource ressource) {
        return OriginalResourceEntity.builder()
                .id(ressource.getId())
                .projectId(ressource.getProjectId())
                .text(ressource.getText())
                .name(ressource.getName())
                .type(ressource.getType())
                .fileType(ressource.getFileType())
                .createDate(ressource.getCreateDate())
                .stateProcessSource(ressource.getStateProcessSource())
                .build();
    }

    public static List<OriginalRessource> listToDomain(List<OriginalResourceEntity> entities) {
        return entities.stream().map(OriginalResourceEntity::toDomain).toList();
    }

    public static List<OriginalResourceEntity> listFromDomain(List<OriginalRessource> ressources) {
        return ressources.stream().map(OriginalResourceEntity::fromDomain).toList();
    }

}