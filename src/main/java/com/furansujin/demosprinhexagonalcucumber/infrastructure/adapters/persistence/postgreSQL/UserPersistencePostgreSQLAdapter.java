package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.UserEntity;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPersistencePostgreSQLAdapter implements UserPersistencePort {

    private final UserEntityRepository userEntityRepository;



    @Override
    public User save(User user) {
        if(user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        UserEntity save = this.userEntityRepository.save(UserEntity.fromDomain(user));
        return UserEntity.toDomain(save);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        Optional<UserEntity> userEntity = this.userEntityRepository.findById(userId);
        return userEntity.map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntity = this.userEntityRepository.findByUsername(username);
        return userEntity.map(UserEntity::toDomain);
    }
}
