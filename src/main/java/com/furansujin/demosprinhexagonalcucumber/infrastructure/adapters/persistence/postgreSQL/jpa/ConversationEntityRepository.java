package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationEntityRepository extends JpaRepository<ConversationEntity, UUID> {
    List<ConversationEntity> findByProjectIdAndUserId(UUID projectId, UUID userId);

    Optional<ConversationEntity> findByIdAndUserId(UUID idConversation, UUID userId);


}
