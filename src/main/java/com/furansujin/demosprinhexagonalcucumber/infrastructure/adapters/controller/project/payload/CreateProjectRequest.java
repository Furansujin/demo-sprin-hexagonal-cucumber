package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.project.payload;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateProjectRequest {
   private final String name;
    private final String description;
}
