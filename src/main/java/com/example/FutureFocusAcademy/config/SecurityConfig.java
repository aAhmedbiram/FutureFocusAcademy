package com.example.FutureFocusAcademy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    authz.requestMatchers("*/swagger-ui.html").permitAll();
                    authz.requestMatchers("*/admin").hasRole("ADMIN");
                    authz.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
                    authz.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
                    authz.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");
                    authz.requestMatchers(HttpMethod.PATCH).hasRole("TEACHER");
                    authz.anyRequest().authenticated();

                }).formLogin(httpSecurityFormLoginConfigurer -> {

                }).httpBasic(httpSecurityHttpBasicConfigurer -> {

                });


        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("myUser")
                .password(passwordEncoder().encode("myPassword"))
                .roles("USER");
        return authenticationManagerBuilder.build();
    }
}
