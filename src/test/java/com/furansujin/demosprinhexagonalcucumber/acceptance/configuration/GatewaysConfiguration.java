package com.furansujin.demosprinhexagonalcucumber.acceptance.configuration;

import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.authentication.InMemory.InMemoryAuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.inMemory.InMemoryLanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.inMemory.InMemoryGithubAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GatewaysConfiguration {



    @Bean
    @Scope("cucumber-glue")
    AuthenticationGateway authenticationGateway() {
        return new InMemoryAuthenticationGateway();
    }

    @Bean
    @Scope("cucumber-glue")
    LanguageModelGateway languageModelGateway() {
        return new InMemoryLanguageModelGateway();
    }
    @Bean
    @Scope("cucumber-glue")
    GithubGateway githubGateway() {
        return new InMemoryGithubAdapter();
    }

}
