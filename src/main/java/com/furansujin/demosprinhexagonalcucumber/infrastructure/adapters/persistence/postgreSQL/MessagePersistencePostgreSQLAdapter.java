package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.MessageEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.MessageEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagePersistencePostgreSQLAdapter implements MessagePersistencePort {

    private final MessageEntityRepository messageEntityRepository;

    @Override
    public UserMessage saveUserMessage(UserMessage message) {
        MessageEntity messageEntity = MessageEntity.fromDomain(message);
        MessageEntity save = messageEntityRepository.save(messageEntity);
        return MessageEntity.userMessagetoDomain(save);
    }

    @Override
    public AssistantMessage saveAssistantMessage(AssistantMessage message) {
        MessageEntity messageEntity = MessageEntity.fromDomain(message);
        MessageEntity save = messageEntityRepository.save(messageEntity);
        return MessageEntity.assistantMessageToDomain(save);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        Optional<MessageEntity> messageEntity = messageEntityRepository.findById(id);
        return messageEntity.map(MessageEntity::toDomain);
    }

    @Override
    public void saveALL(List<Message> userMessage) {
        List<MessageEntity> messageEntities = MessageEntity.listFromDomain(userMessage);
        messageEntityRepository.saveAll(messageEntities);
    }

//    @Override
//    public List<Message> findByConversationIdAndUserID(UUID id, UUID userId) {
//        return MessageEntity.listToDomain(messageEntityRepository.findByConversationIdAndUserID(id, userId));
//
//    }


}
