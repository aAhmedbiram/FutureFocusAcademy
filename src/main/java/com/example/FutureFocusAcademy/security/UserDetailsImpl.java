package com.example.FutureFocusAcademy.security;

import com.example.FutureFocusAcademy.document.SubUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(SubUser user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        // Example additional field
        String fullName = user.getName();
        // Example additional field
        String email = user.getEmail();
    }

    // Implement other UserDetails methods
    // ...

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Other overridden methods...
}
