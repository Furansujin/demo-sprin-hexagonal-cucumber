package com.furansujin.demosprinhexagonalcucumber.domain.entities;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
public class Conversation   {

    private final UUID id;

    private String title;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final User user;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Project project;

    public static Conversation create(  User user, Project project, String title) {
        return Conversation.builder()
                .id(UUID.randomUUID())
                .user(user)
                .title(title)
                .project(project)
                .messages(new ArrayList<>())
                .build();
    }

    public  List<Message> getAndAddMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        if (this.messages.size() == 0){
            this.genererTitre(message.getText());
         }
        List<Message> combinedMessages = new ArrayList<>(this.messages);
        combinedMessages.add(message);
       return  combinedMessages;
    }
    private void genererTitre(String message) {
        // Logique pour générer un titre à partir du contenu du message
        if(message.length() < 25)
            this.title = message  + "...";
        else
            this.title = message.substring(0, 25) + "...";  // exemple simple
    }

}