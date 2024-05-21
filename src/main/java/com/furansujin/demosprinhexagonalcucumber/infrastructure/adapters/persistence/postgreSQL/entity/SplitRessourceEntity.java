package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;



import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "split_ressources")
public class SplitRessourceEntity {

    @Id
    private UUID id;

    private UUID projectId;

    @Column(name = "vector", columnDefinition = "vector")
    private  String vector;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    private String name;

    private Integer position;

    private Integer totalParts;

    @Column(name = "original_ressource_id")
    private UUID originalRessourceId;

    public SplitRessourceEntity(UUID originalRessourceId, String vectorized, String name, String text, Integer position, Integer totalParts) {
        this.id = UUID.randomUUID();
        this.vector = vectorized;
        this.text = text;
        this.name = name;
        this.position = position;
        this.totalParts = totalParts;
        this.originalRessourceId = originalRessourceId;
    }

    public static SplitRessource toDomain(SplitRessourceEntity entity){
        return SplitRessource.builder()
                .id(entity.getId())
//                .vector(entity.getVector())
                .text(entity.getText())
                .name(entity.getName())
                .position(entity.getPosition())
                .totalParts(entity.getTotalParts())
                .originalRessourceId(entity.getOriginalRessourceId())
                .build();
    }

    public static SplitRessourceEntity fromDomain(SplitRessource splitRessource){
        return SplitRessourceEntity.builder()
                .id(splitRessource.getId())
//                .vector(splitRessource.getVector())
                .text(splitRessource.getText())
                .name(splitRessource.getName())
                .position(splitRessource.getPosition())
                .totalParts(splitRessource.getTotalParts())
                .originalRessourceId(splitRessource.getOriginalRessourceId())
                .build();
    }

    public static List<SplitRessource> listToDomain(List<SplitRessourceEntity> entities){
        return entities.stream().map(SplitRessourceEntity::toDomain).toList();
    }

    public static List<SplitRessourceEntity> listFromDomain(List<SplitRessource> splitRessources){
        return splitRessources.stream().map(SplitRessourceEntity::fromDomain).toList();
    }

}
