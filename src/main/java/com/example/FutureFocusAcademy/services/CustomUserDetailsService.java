package com.example.FutureFocusAcademy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.FutureFocusAcademy.model.BaseUser;
import com.example.FutureFocusAcademy.model.UserDetailsImpl;
import com.example.FutureFocusAcademy.repository.UserRepository; // Use the correct repository

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Replace with your user repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = userRepository.findByUsername(username); // Implement this method in your repository
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user); // Modify this to handle your UserDetailsImpl based on BaseUser
    }
}
