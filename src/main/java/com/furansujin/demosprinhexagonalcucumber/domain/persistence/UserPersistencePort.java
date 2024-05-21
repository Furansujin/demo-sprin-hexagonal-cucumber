package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    User save(User user);

    Optional<User> findById(UUID userId);

    Optional<User> findByUsername(String username);
}
