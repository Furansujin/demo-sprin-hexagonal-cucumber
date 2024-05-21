package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.payload;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserProjectRole;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserProjectRolerResponse {


    private final UUID id;

    private final UserResponse user;


    public static UserProjectRolerResponse fromDomain(UserProjectRole userProjectRole) {
        return new UserProjectRolerResponse(userProjectRole.getId(), UserResponse.fromDomain(userProjectRole.getUser()));

    }
    public static List<UserProjectRolerResponse> fromDomain(List<UserProjectRole> userProjectRoles) {
        return userProjectRoles.stream().map(UserProjectRolerResponse::fromDomain).collect(java.util.stream.Collectors.toList());

    }
}
