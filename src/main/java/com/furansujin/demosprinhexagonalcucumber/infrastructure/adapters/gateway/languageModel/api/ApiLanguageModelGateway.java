package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.chat.ChatRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.chat.ChatResponse;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.embedding.EmbeddingRequest;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.embedding.EmbeddingResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class ApiLanguageModelGateway implements LanguageModelGateway {



    @Qualifier("openaiRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${openai.model.completions}")
    private String modelCompletions;

    @Value("${openai.model.embeddings}")
    private String modelEmbeddings;

    @Value("${openai.api.url.completions}")
    private String apiUrlCompletions;

    @Value("${openai.api.url.embeddings}")
    private String apiUrlEmbeddings;


    @Override
    public String getAnswerAssistant(List<Message> messages, String personaPromnt) {

        // create a request
        ChatRequest request = new ChatRequest(modelCompletions, messages, personaPromnt);

        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrlCompletions, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public float[] vectorize(String textOriginal) {
        EmbeddingRequest embeddingRequest = new EmbeddingRequest(
                modelEmbeddings,
                List.of(textOriginal)
        );

        EmbeddingResult embeddingResult = restTemplate.postForObject(apiUrlEmbeddings, embeddingRequest, EmbeddingResult.class);

        List<Double> embedding = embeddingResult.getData().get(0).getEmbedding();
        float[] vector = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            vector[i] = embedding.get(i).floatValue();
        }
        return vector;
    }

    //
//    private OkHttpClient client;
//    private ChatGPT chatGPT;
//    @Value("${ChatGPT.apiKey}")
//    private String apiKey;
//    public ApiLanguageModelGateway() {
//        this.client = new OkHttpClient();
//    }
//    public ApiLanguageModelGateway(String apiKey) {
//        this.client = new OkHttpClient();
//        this.apiKey = apiKey;
//    }
//    @Override
//    public String getAnswerAssistant(List<Message> messages) {
//        List<com.lilittlecat.chatgpt.offical.entity.Message> messagesGpt = new ArrayList<>();
//        for (Message message : messages) {
//            com.lilittlecat.chatgpt.offical.entity.Message messageResource = new com.lilittlecat.chatgpt.offical.entity.Message(DEFAULT_USER, message.getOriginalText());
//
//            messagesGpt.add(messageResource);
//        }
//        chatGPT = new ChatGPT(apiKey, client);
//        try {
//            return chatGPT.ask(messagesGpt);
//        } catch (Exception e) {
//            System.out.println(  e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public float[] vectorize(String textOriginal) {
//        OpenAiService aiService =  new OpenAiService(apiKey);
//        EmbeddingResult calculatedEmbedding = aiService.createEmbeddings(new EmbeddingRequest(
//                "text-embedding-ada-002",
//                List.of(textOriginal),
//                null
//        ));
//
//        List<Double> embedding = calculatedEmbedding.getData().get(0).getEmbedding();
//        float[] vector = new float[embedding.size()];
//        for (int i = 0; i < embedding.size(); i++) {
//            vector[i] = embedding.get(i).floatValue();
//        }
//        return vector;
//    }

}
