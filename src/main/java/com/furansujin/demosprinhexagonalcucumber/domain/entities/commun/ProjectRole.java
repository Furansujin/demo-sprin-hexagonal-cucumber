package com.furansujin.demosprinhexagonalcucumber.domain.entities.commun;

public enum ProjectRole {
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private final String value;

    ProjectRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}