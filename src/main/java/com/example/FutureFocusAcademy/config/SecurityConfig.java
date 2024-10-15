package com.example.FutureFocusAcademy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean(name = "appPasswordEncoder") // Specify the bean name here
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Remove the field injection of PasswordEncoder
}
