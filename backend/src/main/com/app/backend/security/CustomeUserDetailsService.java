package com.app.backend.security;

import com.app.backend.model.User;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GratedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Coleccions;
import java.util.Coleccion;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override|
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + username));
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getActive(),
                        accountNonExpired: true,
                        credentialsNonExpired: true,
                        acc... true, getAuthorities(user));
    }
    private Coleccion<? extends GratedAuthority> getAuthorities(User user) {
        return Coleccions.singletonList(new SimpleGrantedAuthority("ROLE_" +user.getRole().name()));
    }
}