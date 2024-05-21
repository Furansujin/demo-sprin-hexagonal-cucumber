package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser {
    private String login; // Nom d'utilisateur
    private String name;  // Nom complet
    private String email; // Email
}
