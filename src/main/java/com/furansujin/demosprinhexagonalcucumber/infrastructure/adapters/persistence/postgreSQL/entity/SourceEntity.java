package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sources")
public class SourceEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SourceType type;

    private String accessToken;

    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private UUID userId;

    public SourceEntity(SourceType type, String accessToken, String userName, UUID userId) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.accessToken = accessToken;
        this.userName = userName;
        this.userId = userId;
    }

    public static Source toDomain(SourceEntity entity) {
        return Source.builder()
                .id(entity.getId())
                .type(entity.getType())
                .accessToken(entity.getAccessToken())
                .userName(entity.getUserName())
                .userId(entity.getUserId())
                .build();
    }

    public static SourceEntity fromDomain(Source source) {
        return SourceEntity.builder()
                .id(source.getId())
                .type(source.getType())
                .accessToken(source.getAccessToken())
                .userName(source.getUserName())
                .userId(source.getUserId())
                .build();
    }

    public static Set<Source> listToDomain(Set<SourceEntity> entities) {
        return entities.stream().map(SourceEntity::toDomain).collect(Collectors.toSet());
    }

    public static Set<SourceEntity> listFromDomain(Set<Source> sources) {
        return sources.stream().map(SourceEntity::fromDomain).collect(Collectors.toSet());
    }
}