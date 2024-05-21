package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Source   {


    private UUID id;

    private SourceType type;

    private String accessToken;

    private String userName;

    private UUID userId;

 public Source(SourceType type, String accessToken, UUID userId, String userName) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.accessToken = accessToken;
        this.userId = userId;
        this.userName = userName;
    }




    // Fields for authentication/authorization (e.g., apiKey, oauthToken, etc.)

    //    @ManyToOne
    //    @JoinColumn(name = "project_id", nullable = false)
    //    private Project project;

}