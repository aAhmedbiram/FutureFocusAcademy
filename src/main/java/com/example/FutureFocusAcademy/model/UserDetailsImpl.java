package com.example.FutureFocusAcademy.model;

import com.example.FutureFocusAcademy.document.SubUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final BaseUser user;

    public UserDetailsImpl(SubUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the appropriate authorities for the user
        return null; // You can customize this based on roles like Student, Teacher, Admin
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Or implement logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or implement logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or implement logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Or implement logic
    }
}
