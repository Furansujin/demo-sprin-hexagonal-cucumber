package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.SplitRessourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SplitRessourceEntityRepository  extends JpaRepository<SplitRessourceEntity, UUID> {


//    @Query(value = "SELECT s FROM SplitRessourceEntity s WHERE 1 - cosine_distance(s.vector, :vector) > :minSimilarity ORDER BY 1 - cosine_distance(s.vector, :vector) DESC")
//    Page<SplitRessourceEntity> findSimilarSplitRessources(
//            @Param("vector") float[] vector,
//            @Param("minSimilarity") float minSimilarity,
//            Pageable pageable);


    @Query(value = "SELECT s FROM SplitRessourceEntity s WHERE 1 - cosine_distance(s.vector, :vector) > :minSimilarity ORDER BY 1 - cosine_distance(s.vector, :vector) DESC LIMIT :lim")
    List<SplitRessourceEntity> findSimilarSplitRessources(
            @Param("vector") float[] vector,
            @Param("minSimilarity") float minSimilarity,
            @Param("lim") int lim);
//    @Query("SELECT s FROM SplitRessourceEntity s WHERE FUNCTION('cosine_distance', s.vector, :vector) > :minSimilarity ORDER BY FUNCTION('cosine_distance', s.vector, :vector) DESC")
//    List<SplitRessourceEntity> findSimilarSplitRessources(@Param("vector") float[] vector, @Param("minSimilarity") float minSimilarity, Pageable pageable);

}
