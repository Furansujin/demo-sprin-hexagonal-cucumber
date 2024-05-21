package com.furansujin.demosprinhexagonalcucumber.acceptance.configuration;

import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ConversationPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.MessagePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryConversationPersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryMessagePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryOriginalRessourcePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryProjectPersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySourceDownloaderPersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySourcePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySplitRessourcePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryUserPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RepositoriesConfiguration {
    @Bean
    @Scope("cucumber-glue")
    public ConversationPersistencePort conversationPersistencePort() {
        return new InMemoryConversationPersistenceAdapter( this.messagePersistencePort());
    }

    @Bean
    @Scope("cucumber-glue")
    public MessagePersistencePort messagePersistencePort() {
        return new InMemoryMessagePersistenceAdapter();
    }

    @Bean
    @Scope("cucumber-glue")
    public ProjectPersistencePort projectPersistencePort() {
        return new InMemoryProjectPersistenceAdapter(this.conversationPersistencePort(), this.splitRessourcePersistencePort());
    }
    @Bean
    @Scope("cucumber-glue")
    public SourcePersistencePort sourcePersistencePort() {
        return new InMemorySourcePersistenceAdapter();
    }

    @Bean
    @Scope("cucumber-glue")
    public SourceDownloaderPersistencePort sourceDownloaderPersistencePort() {
        return new InMemorySourceDownloaderPersistenceAdapter();
    }

    @Bean
    @Scope("cucumber-glue")
    public SplitRessourcePersistencePort splitRessourcePersistencePort() {
        return new InMemorySplitRessourcePersistenceAdapter();
    }
    @Bean
    @Scope("cucumber-glue")
    public UserPersistencePort userPersistencePort() {
        return new InMemoryUserPersistenceAdapter();
    }

    @Bean
    @Scope("cucumber-glue")
    public OriginalRessourcePersistencePort originalRessourcePersistencePort() {
        return new InMemoryOriginalRessourcePersistenceAdapter();
    }

}

