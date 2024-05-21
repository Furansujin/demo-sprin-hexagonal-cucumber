package com.furansujin.demosprinhexagonalcucumber.domain.entities.commun;

public enum MessageSenderType {
    ASSISTANT("assistant"), USER("user"), SYSTEM("system");

    public final String sender;
    MessageSenderType(String sender) {
        this.sender = sender;
    }
}