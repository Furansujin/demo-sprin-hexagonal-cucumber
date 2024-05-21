package com.furansujin.demosprinhexagonalcucumber.infrastructure.config;


import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.AuthenticateUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.RegisterUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation.CreateConversationUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation.GetAllConversationProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.CreateMessageUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.message.GetAllMessageConversationUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.CreateProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.GetAllProjectUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.project.InviteUserInProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.AuthGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.DownloadTaskFileGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.FetchReposGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.ProcessOriginalRessourceUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.SelectRepositoryAndCreateTaskFileGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.user.GetProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuracion BEANS
 */
@Configuration
@RequiredArgsConstructor
public class UseCaseBeanConfiguration {

    /*
     * User  Use Cases
     */
    @Bean
    public GetProfileUseCase getProfileUseCase(AuthenticationGateway authenticationGateway) {
        return new GetProfileUseCase(authenticationGateway);
    }

    /*
     * Source  Use Cases
     */
    @Bean
    public AuthGithubUseCase authGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort) {
        return new AuthGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort);
    }
    @Bean
    public DownloadTaskFileGithubUseCase downloadTaskFileGithubUseCase(SourceDownloaderPersistencePort sourceDownloaderPersistencePort, GithubGateway githubGateway,
                                                                       UserPersistencePort userPersistencePort, ProjectPersistencePort projectPersistencePort,
                                                                       SourcePersistencePort sourcePersistencePort, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        return new DownloadTaskFileGithubUseCase(sourceDownloaderPersistencePort, githubGateway, userPersistencePort, projectPersistencePort, sourcePersistencePort, originalRessourcePersistencePort);
    }
    @Bean
    public FetchReposGithubUseCase fetchReposGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort) {
        return new FetchReposGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort);
    }
    @Bean
    public ProcessOriginalRessourceUseCase processOriginalRessourceUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort, SourcePersistencePort sourcePersistencePort,
                                                                           SourceDownloaderPersistencePort sourceDownloaderPersistencePort, OriginalRessourcePersistencePort originalRessourcePersistencePort,
                                                                           LanguageModelGateway languageModelGateway, SplitRessourcePersistencePort splitRessourcePersistencePort) {
        return new ProcessOriginalRessourceUseCase(authenticationGateway,  projectPersistencePort, sourcePersistencePort, sourceDownloaderPersistencePort, originalRessourcePersistencePort, languageModelGateway, splitRessourcePersistencePort);
    }

    @Bean
    public SelectRepositoryAndCreateTaskFileGithubUseCase selectRepositoryAndCreateTaskFileGithubUseCase(GithubGateway githubGateway, AuthenticationGateway authenticationGateway,
                                                                                                         SourcePersistencePort sourcePersistencePort, SourceDownloaderPersistencePort sourceDownloaderPersistencePort) {
        return new SelectRepositoryAndCreateTaskFileGithubUseCase(githubGateway, authenticationGateway, sourcePersistencePort, sourceDownloaderPersistencePort);
    }

    /*
     * Project  Use Cases
     */
    @Bean
    public CreateProjectUseCase createProjectUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        return new CreateProjectUseCase(authenticationGateway, projectPersistencePort);
    }

    @Bean
    public GetAllProjectUserUseCase getAllProjectUserUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort) {
        return new GetAllProjectUserUseCase(authenticationGateway, projectPersistencePort);
    }
    @Bean
    public InviteUserInProjectUseCase inviteUserInProjectUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort, UserPersistencePort userPersistencePort) {
        return new InviteUserInProjectUseCase(authenticationGateway, projectPersistencePort, userPersistencePort);
    }

    /*
     * Message  Use Cases
     */

    @Bean
    public CreateMessageUseCase createMessageUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort,
                                                     ConversationPersistencePort conversationPersistencePort,
                                                     SplitRessourcePersistencePort splitRessourcePersistencePort, MessagePersistencePort messagePersistencePort,
                                                     LanguageModelGateway languageModelGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        return new CreateMessageUseCase(authenticationGateway, projectPersistencePort, conversationPersistencePort, splitRessourcePersistencePort, messagePersistencePort, languageModelGateway, originalRessourcePersistencePort);
    }

    @Bean
    public GetAllMessageConversationUseCase getAllMessageConversationUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort) {
        return new GetAllMessageConversationUseCase(authenticationGateway, conversationPersistencePort);
    }

    /*
     * Conversation  Use Cases
     */

    @Bean
    public GetAllConversationProjectUseCase getAllConversationProjectUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort, ProjectPersistencePort projectPersistencePort) {
        return new GetAllConversationProjectUseCase(authenticationGateway, conversationPersistencePort, projectPersistencePort);
    }
    @Bean
    public CreateConversationUseCase createConversationUseCase(AuthenticationGateway authenticationGateway, ConversationPersistencePort conversationPersistencePort, ProjectPersistencePort projectPersistencePort) {
        return new CreateConversationUseCase(authenticationGateway, conversationPersistencePort, projectPersistencePort);
    }

    /*
     * Authentication  Use Cases
     */
    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(  ) {
        return new AuthenticateUserUseCase(   );
    }

//    @Bean
//    public RegisterUserUseCase registerUserUseCase(PasswordEncoder encoder, UserPersistencePort userPersistencePort) {
//        return new RegisterUserUseCase(encoder, userPersistencePort);
//    }
    @Bean
    public RegisterUserUseCase registerUserUseCase(UserPersistencePort userPersistencePort) {
        return new RegisterUserUseCase(  userPersistencePort);
    }
}
