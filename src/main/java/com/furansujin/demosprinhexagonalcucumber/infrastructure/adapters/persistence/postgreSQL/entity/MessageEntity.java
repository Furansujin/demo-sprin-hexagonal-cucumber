package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.MessageSenderType;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity implements Message {

    @Id
    private UUID id;

    @Column(name = "original_text", columnDefinition = "TEXT")
    private String originalText;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private MessageSenderType type;

    private float[] embeddingVectorizedText;

    @Column(name = "enriched_text", columnDefinition = "TEXT")
    private String enrichedText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private ConversationEntity conversation;

    @Column(name = "conversation_id")
    private UUID conversationId;

    public static Message toDomain(MessageEntity entity) {
        if(entity.getType().equals(MessageSenderType.USER)) {
            return UserMessage.builder()
                    .id(entity.getId())
                    .originalText(entity.getOriginalText())
                    .date(entity.getDate())
                    .embeddingVectorizedText(entity.getEmbeddingVectorizedText())
                    .enrichedText(entity.getEnrichedText())
//                    .conversation(ConversationEntity.toDomain(entity.getConversationEntity()))
                    .build();
        } else {

            return AssistantMessage.builder()
                    .id(entity.getId())
                    .originalText(entity.getOriginalText())
                    .date(entity.getDate())
//                    .conversation(ConversationEntity.toDomain(entity.getConversationEntity()))
                    .build();
        }
    }
    public static UserMessage userMessagetoDomain(MessageEntity entity) {
            return UserMessage.builder()
                    .id(entity.getId())
                    .originalText(entity.getOriginalText())
                    .date(entity.getDate())
                    .embeddingVectorizedText(entity.getEmbeddingVectorizedText())
                    .enrichedText(entity.getEnrichedText())
//                    .conversation(ConversationEntity.toDomain(entity.getConversationEntity()))
                    .build();

    }
    public static AssistantMessage assistantMessageToDomain(MessageEntity entity) {
            return AssistantMessage.builder()
                    .id(entity.getId())
                    .originalText(entity.getOriginalText())
                    .date(entity.getDate())
//                    .conversation(ConversationEntity.toDomain(entity.getConversationEntity()))
                    .build();
    }

    public static MessageEntity fromDomain(Message message) {
        if(message.getType().equals(MessageSenderType.USER)) {
            UserMessage userMessage = (UserMessage) message;
            return MessageEntity.builder()
                    .id(userMessage.getId())
                    .originalText(userMessage.getOriginalText())
                    .date(userMessage.getDate())
                    .type(userMessage.getType())
                    .embeddingVectorizedText(userMessage.getEmbeddingVectorizedText())
                    .enrichedText(userMessage.getEnrichedText())
                    .conversationId(userMessage.getConversation().getId())
//                    .conversation(ConversationEntity.fromDomain(userMessage.getConversation()))
                    .build();

        } else {
            AssistantMessage assistantMessage = (AssistantMessage) message;
            return MessageEntity.builder()
                    .id(assistantMessage.getId())
                    .originalText(assistantMessage.getOriginalText())
                    .date(assistantMessage.getDate())
                    .type(assistantMessage.getType())
                    .conversationId(assistantMessage.getConversation().getId())
//                    .conversation(ConversationEntity.fromDomain(assistantMessage.getConversation()))
                    .build();
        }
    }

    public static List<Message> listToDomain(List<MessageEntity> entities) {
        return entities.stream().map(MessageEntity::toDomain).toList();
    }

    public static List<MessageEntity> listFromDomain(List<Message> messages) {
        return messages.stream().map(MessageEntity::fromDomain).toList();
    }

    @Override
    public String getText() {
        return originalText;
    }

    @Override
    public String getTextProcessed() {
        return enrichedText;
    }

    @Override
    public Conversation getConversation() {
        return ConversationEntity.toDomain(this.conversation);
    }

    public ConversationEntity getConversationEntity() {
        return this.conversation;
    }

}