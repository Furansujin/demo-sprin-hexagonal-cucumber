package com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;

import java.util.List;

public interface LanguageModelGateway {

    String  getAnswerAssistant(List<Message> messages, String personaPromnt);

    public float[] vectorize(String textOriginal);

}
