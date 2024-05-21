package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversations")
public class ConversationEntity {

    @Id
    private UUID id;

    private String title;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageEntity> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    public static Conversation toDomain(ConversationEntity entity) {
           return Conversation.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .messages(MessageEntity.listToDomain(entity.getMessages()))
                    .user(UserEntity.toDomain(entity.getUser()))
                    .project(Project.builder().id(entity.getProject().getId()).build())
                    .build();
        }

    public static ConversationEntity fromDomain(Conversation conversation) {
        return ConversationEntity.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .messages(MessageEntity.listFromDomain(conversation.getMessages()))
                .user(UserEntity.fromDomain(conversation.getUser()))
                  .project(ProjectEntity.builder().id(conversation.getProject().getId()).build())
                .build();
    }


}