package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.AuthProvider;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    private String username;

    private String password;

    private String gender;

    private String locale;

    private String familyName;

    private String givenName;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private String picture;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> authority = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SourceEntity> sources = new HashSet<>();


    @OneToMany(mappedBy = "user")
    private Set<UserProjectRoleEntity> projectRoles;

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        user.setGender(entity.getGender());
        user.setLocale(entity.getLocale());
        user.setFamilyName(entity.getFamilyName());
        user.setGivenName(entity.getGivenName());
        user.setEnabled(entity.isEnabled());
        user.setProvider(entity.getProvider());
        user.setProviderId(entity.getProviderId());
        user.setPicture(entity.getPicture());
        user.setAuthority(entity.getAuthority());
//        user.setSources(SourceEntity.listToDomain(entity.getSources()));
        return user;
    }

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .gender(user.getGender())
                .locale(user.getLocale())
                .familyName(user.getFamilyName())
                .givenName(user.getGivenName())
                .enabled(user.isEnabled())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .picture(user.getPicture())
                .authority(user.getAuthority())
                .sources(SourceEntity.listFromDomain(user.getSources()))
                .build();
    }

    public static List<User> listToDomain(List<UserEntity> entities) {
        return entities.stream().map(UserEntity::toDomain).toList();
    }

    public static List<UserEntity> listFromDomain(List<User> users) {
        return users.stream().map(UserEntity::fromDomain).toList();
    }

}  