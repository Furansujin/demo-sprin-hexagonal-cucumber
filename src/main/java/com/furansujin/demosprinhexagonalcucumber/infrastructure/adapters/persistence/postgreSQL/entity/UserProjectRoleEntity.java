package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.UserProjectRole;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.ProjectRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_project_roles")
public class UserProjectRoleEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role;

    public static UserProjectRole toDomain(UserProjectRoleEntity entity) {
        return UserProjectRole.builder()
                .id(entity.getId())
                .user(UserEntity.toDomain(entity.getUser()))
                .role(entity.getRole())
                .build();
    }

    public static UserProjectRoleEntity fromDomain(UserProjectRole role) {
        return UserProjectRoleEntity.builder()
                .id(role.getId())
                .user(UserEntity.fromDomain(role.getUser()))
                .role(role.getRole())
                .build();
    }

    public static List<UserProjectRole> listToDomain(List<UserProjectRoleEntity> entities) {
        return entities.stream().map(UserProjectRoleEntity::toDomain).toList();
    }

    public static List<UserProjectRoleEntity> listFromDomain(List<UserProjectRole> roles) {
        return roles.stream().map(role -> UserProjectRoleEntity.fromDomain(role)).toList();
    }
}