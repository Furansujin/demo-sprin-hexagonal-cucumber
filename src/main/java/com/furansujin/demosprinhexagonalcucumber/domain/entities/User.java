package com.furansujin.demosprinhexagonalcucumber.domain.entities;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.AuthProvider;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User    {

    private UUID id;

    private String username;

    private String password;

    private String gender;

    private String locale;

    private String familyName;

    private String givenName;

    private boolean enabled;

    private AuthProvider provider;

    private String providerId;

    private String picture;

    private Set<Role> authority = new HashSet<>();
    
    private Set<Source> sources = new HashSet<>();

    private Set<UserProjectRole> projectRoles = new HashSet<>();



}  