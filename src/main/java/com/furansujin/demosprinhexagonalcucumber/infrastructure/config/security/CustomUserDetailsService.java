//package com.miraisystems.getsensebackend.infrastructure.config.security;
//
//
//import com.miraisystems.getsensebackend.domain.entities.User;
//import com.miraisystems.getsensebackend.domain.exception.NotFoundException;
//import com.miraisystems.getsensebackend.domain.persistence.UserPersistencePort;
//import jakarta.transaction.Transactional;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserPersistencePort userPersistencePort;
//
//    public CustomUserDetailsService(UserPersistencePort userPersistencePort) {
//        this.userPersistencePort = userPersistencePort;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userPersistencePort.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        return UserPrincipal.create(user);
//    }
//    @Transactional
//    public UserDetails loadUserById(UUID id) {
//        User user = userPersistencePort.findById(id).orElseThrow(() -> new NotFoundException("Can't find user by ID"));
//
//        return UserPrincipal.create(user);
//    }
//}
