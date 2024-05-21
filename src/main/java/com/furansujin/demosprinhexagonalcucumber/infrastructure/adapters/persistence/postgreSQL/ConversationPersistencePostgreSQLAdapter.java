package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.ConversationEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.ConversationEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationPersistencePostgreSQLAdapter implements ConversationPersistencePort {

    private final ConversationEntityRepository conversationEntityRepository;

    @Override
    public Conversation save(Conversation conversation) {
        ConversationEntity conversationEntity = ConversationEntity.fromDomain(conversation);

        ConversationEntity save = this.conversationEntityRepository.save(conversationEntity);
        return ConversationEntity.toDomain(save);
    }

    @Override
    public List<Conversation> findByProjectIdAndUserId(UUID projectId, UUID userId) {
       return  this.conversationEntityRepository.findByProjectIdAndUserId(projectId, userId).stream()
               .map(ConversationEntity::toDomain)
               .toList();
    }

    @Override
    public Optional<Conversation> findByIdAndUserId(UUID idConversation, UUID userId) {
        return this.conversationEntityRepository.findByIdAndUserId(idConversation, userId).map(ConversationEntity::toDomain);
    }

    @Override
    public void delete(UUID id) {
        this.conversationEntityRepository.deleteById(id);
    }
}
