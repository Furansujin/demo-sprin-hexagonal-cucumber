package com.furansujin.demosprinhexagonalcucumber.domain.usecases.message;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.AssistantMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Conversation;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserMessage;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateMessageUseCase implements UseCase<List<Message>, CreateMessageUseCase.CreateMessageRequest> {

    public static final int LIMIT_SIMILAR_SPLIT_RESSOURCE = 10;
    public static final float MIN_SIMILARITY_RESSOURCE = 0.7f;
    private final AuthenticationGateway authenticationGateway;
    private final ConversationPersistencePort conversationPersistencePort;
    private final SplitRessourcePersistencePort splitRessourcePersistencePort;

    private final ProjectPersistencePort projectPersistencePort;
    private final MessagePersistencePort messagePersistencePort;
    private final LanguageModelGateway languageModelGateway;
    private final OriginalRessourcePersistencePort originalRessourcePersistencePort;

    public CreateMessageUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort,
                                ConversationPersistencePort conversationPersistencePort,
                                SplitRessourcePersistencePort splitRessourcePersistencePort, MessagePersistencePort messagePersistencePort,
                                LanguageModelGateway languageModelGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.conversationPersistencePort = conversationPersistencePort;
        this.splitRessourcePersistencePort = splitRessourcePersistencePort;
        this.projectPersistencePort = projectPersistencePort;
        this.messagePersistencePort = messagePersistencePort;
        this.languageModelGateway = languageModelGateway;
        this.originalRessourcePersistencePort = originalRessourcePersistencePort;
    }

    @Override
    public List<Message> execute(CreateMessageRequest arg) throws Exception {
        User user = this.authenticationGateway.currentUser().orElseThrow(() -> new Exception("User not logged in"));
        Project project = this.projectPersistencePort.findByIdAndUserId(arg.idProject(), user.getId()).orElseThrow(() -> new Exception("Project not found"));
        boolean existProject = this.originalRessourcePersistencePort.existsByProjectId(project.getId());
        if(!existProject)
            throw new Exception("No ressource in project, link project to ressource");

        Conversation conversation = this.conversationPersistencePort.findByIdAndUserId(arg.idConversation, user.getId())
                .orElseGet(() -> Conversation.create(user, project, "title"));




        float[] vector = this.languageModelGateway.vectorize(arg.originalText());

        String enrichedText = this.getEnrichedText(arg.originalText(), vector);

        UserMessage userMessage = new UserMessage(arg.originalText(), vector, enrichedText, conversation);

//        UserMessage userMessageSaved = this.messagePersistencePort.saveUserMessage(userMessage);

        List<Message> messages = conversation.getAndAddMessage(userMessage);



        String answerAssistant = this.languageModelGateway.getAnswerAssistant(messages, getPersonaPromnt(project));



        AssistantMessage assistantMessage = new AssistantMessage(answerAssistant, conversation);

//        AssistantMessage assistantMessageSaved = this.messagePersistencePort.saveAssistantMessage(assistantMessage);

//        conversation.getMessages().add(assistantMessageSaved);

//        this.conversationPersistencePort.save(conversation);

        this.messagePersistencePort.saveALL(List.of(userMessage, assistantMessage));
//        conversation.ajouterMessage(userMessage);
//        conversation.ajouterMessage(assistantMessage);
//        this.conversationPersistencePort.save(conversation);

//        messages.add(assistantMessage);


        return List.of(userMessage, assistantMessage);
    }

    private String getEnrichedText(String originalText, float[] vector) throws Exception {
        // get similar split ressource
        List<SplitRessource> similarSplitRessource = this.splitRessourcePersistencePort.getSimilarSplitRessource(vector, MIN_SIMILARITY_RESSOURCE, LIMIT_SIMILAR_SPLIT_RESSOURCE)
                .orElseThrow(() -> new Exception("No similar split ressource found"));
        // concat all similar split ressource
        String concatResource = similarSplitRessource
                .stream()
                .map(ressource -> "fichier name : '" + ressource.getName() + "' contenu: '" + ressource.getText() + "' ")
                .collect(Collectors.joining(", "));
        // concat original text and similar split ressource
        return new StringBuilder().append(concatResource).append(originalText).toString();
    }


    private String getPersonaPromnt(Project project){
        return  "You created this project : "+ project.getName()+".".trim();
                
    }

    /**
     * CreateMessageRequest
     * @param originalText
     * @param idConversation null if new conversation
     * @param idProject
     */
    public record CreateMessageRequest(String originalText, UUID idConversation, UUID idProject) {

    }
}
