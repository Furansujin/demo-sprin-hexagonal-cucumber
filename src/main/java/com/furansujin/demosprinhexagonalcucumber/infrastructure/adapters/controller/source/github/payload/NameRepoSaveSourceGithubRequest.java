package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.source.github.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class NameRepoSaveSourceGithubRequest {

    private String nameRepo;
    private UUID projectId;

}
