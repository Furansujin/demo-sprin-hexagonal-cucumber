package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {

    private final UUID id;
    private final String familyName;
    private final String givenName;
    private final String picture;

    public static UserResponse fromDomain(User user) {
        return new UserResponse(user.getId(), user.getFamilyName(), user.getGivenName(), user.getPicture());
    }

    public static List<UserResponse> fromDomain(List<User> users) {
        return users.stream().map(UserResponse::fromDomain).collect(java.util.stream.Collectors.toList());
    }
}
