package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

    
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.SplitRessourceEntityRepository;
import com.pgvector.PGvector;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SplitRessourcePersistenceAdapter implements SplitRessourcePersistencePort {


    private final SplitRessourceEntityRepository splitRessourceEntityRepository;
    private final JdbcTemplate jdbcTemplate;


//    @Override
//    public Optional<List<SplitRessource>> getSimilarSplitRessource(float[] vector, float minSimilarity, int limit) {
////        Pageable limitPageable = PageRequest.of(0, limit);
//        List<SplitRessourceEntity> similarSplitRessources = this.splitRessourceEntityRepository.findSimilarSplitRessources(vector, minSimilarity, limit);
//        return Optional.of(SplitRessourceEntity.listToDomain(similarSplitRessources));
//    }


    public Optional<List<SplitRessource>> getSimilarSplitRessource(float[] vector, float minSimilarity, int limit) {
        return Optional.of(jdbcTemplate.query("""
            SELECT *
            FROM (
                SELECT
                    s.id,
                    s.project_id,
                    s.vector,
                    s.text,
                    s.name,
                    s.position,
                    s.total_parts,
                    s.original_ressource_id,
                    1 - cosine_distance(s.vector, ?) AS distance
                FROM split_ressources s
            ) AS subquery
            WHERE distance > ?
            ORDER BY distance DESC
            LIMIT ?""",
                new Object[]{new PGvector(vector), minSimilarity, limit},
                (row, idx) -> mapRowToSplitRessource(row)));


    }

    private static SplitRessource mapRowToSplitRessource(ResultSet row) throws SQLException {
        if(row.getString("distance") != null){
            System.out.println(row.getString("distance"));

        }

        Array sqlArray = row.getArray("vector");
        if (sqlArray != null) {
            Float[] floatObjectArray = (Float[]) sqlArray.getArray();
            // Convert to primitive float[] if necessary
            float[] vector = new float[floatObjectArray.length];
            for (int i = 0; i < floatObjectArray.length; i++) {
                vector[i] = floatObjectArray[i]; // Auto-unboxing
            }

        return SplitRessource.builder()
                .id(UUID.fromString(row.getString("id")))
                .projectId(UUID.fromString(row.getString("project_id")))
                .vector(vector)
                .text(row.getString("text"))
                .name(row.getString("name"))
                .position(row.getInt("position"))
                .totalParts(row.getInt("total_parts"))
                .originalRessourceId(UUID.fromString(row.getString("original_ressource_id")))
                .build();
        } else {
            throw new RuntimeException("vector is null");
        }
    }

    @Override
    public void saveAll(List<SplitRessource> splitRessources)  {
        splitRessources.forEach(splitRessource -> {
            try {
                save(splitRessource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
//        splitRessourceEntityRepository.saveAll(SplitRessourceEntity.listFromDomain(splitRessources));
    }

    private void  save(SplitRessource splitRessource ) throws SQLException {
        jdbcTemplate.update("Insert into split_ressources(id, project_id, vector, text, name, position, total_parts, original_ressource_id) VALUES (?,?,?,?,?,?,?,?);",
                splitRessource.getId(),
                splitRessource.getProjectId(),
                new PGvector(splitRessource.getVector()),
                splitRessource.getText(),
                splitRessource.getName(),
                splitRessource.getPosition(),
                splitRessource.getTotalParts(),
                splitRessource.getOriginalRessourceId()
        );
    }
}