package com.furansujin.demosprinhexagonalcucumber.infrastructure.config;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<UUID> {



    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            User currentUser = (User) authentication.getPrincipal();
            return Optional.ofNullable(currentUser.getId());
        }

        return Optional.empty();
    }
}