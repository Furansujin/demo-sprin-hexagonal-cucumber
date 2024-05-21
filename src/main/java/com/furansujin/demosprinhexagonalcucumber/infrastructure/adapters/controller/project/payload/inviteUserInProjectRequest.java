package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class inviteUserInProjectRequest {

    private final String emailUser;
    private final UUID idProject;
}
