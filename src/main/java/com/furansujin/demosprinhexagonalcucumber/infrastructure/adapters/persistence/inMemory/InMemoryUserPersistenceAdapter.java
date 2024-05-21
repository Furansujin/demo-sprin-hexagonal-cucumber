package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@RequiredArgsConstructor
public class InMemoryUserPersistenceAdapter implements UserPersistencePort {

    private Set<User> users = new HashSet<>();


    @Override
    public User save(User user) {
        this.users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return this.users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}
