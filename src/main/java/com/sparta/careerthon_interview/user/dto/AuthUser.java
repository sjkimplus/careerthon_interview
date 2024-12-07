package com.sparta.careerthon_interview.user.dto;

import com.sparta.careerthon_interview.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {
    private final long id;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(long id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.authorities = List.of(new SimpleGrantedAuthority(role.name()));
    }
}