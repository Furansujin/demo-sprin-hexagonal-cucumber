package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;

import java.util.List;

public class InMemoryLanguageModelGateway implements LanguageModelGateway {

    String message = "Hello, I am the assistant";
    @Override
    public String getAnswerAssistant(List<Message> messages, String personaPromnt) {
        return message;
    }

    @Override
    public float[] vectorize(String textOriginal) {
        if(message != null)
             return new float[0];
        throw new RuntimeException("Error");
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
