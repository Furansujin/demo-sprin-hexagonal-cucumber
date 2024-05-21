package com.furansujin.demosprinhexagonalcucumber.infrastructure.config.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAIRestTemplateConfig {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Create a list to hold our interceptors
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        // Add an interceptor to add the Authorization header
        interceptors.add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });

        // Add another interceptor to add the Content-Type header
        interceptors.add((request, body, execution) -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        });

        // Set the interceptors to the RestTemplate
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}