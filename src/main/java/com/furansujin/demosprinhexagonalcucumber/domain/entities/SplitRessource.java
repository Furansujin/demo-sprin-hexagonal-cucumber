package com.furansujin.demosprinhexagonalcucumber.domain.entities;



import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SplitRessource   {


    private UUID id;

    private UUID projectId;

     private  float[] vector;


    private String text;

    private String name;

    private Integer position;

    private Integer totalParts;


    private UUID originalRessourceId;

    public SplitRessource(UUID originalRessourceId, UUID projectId, float[] vectorized, String name, String text, Integer position, Integer totalParts) {
        this.id = UUID.randomUUID();
        this.projectId = projectId;
        this.vector = vectorized;
        this.text =text;
        this.name = name;
        this.position = position;
        this.totalParts = totalParts;
        this.originalRessourceId = originalRessourceId;
    }
}
