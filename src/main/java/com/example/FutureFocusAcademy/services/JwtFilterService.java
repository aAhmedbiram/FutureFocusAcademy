package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.model.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Component
public class JwtFilterService extends OncePerRequestFilter {
    @Autowired
    @Lazy
    JwtUtils jwtUtils;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);

                if (!jwtUtils.isValid(token)) {
                    throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
                }

                TokenInfo tokenInfo = jwtUtils.extractInfo(token);

                if (!userDetailsService.isValid(tokenInfo)) { // Fix logic
                    throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
                }

                List<GrantedAuthority> authorities =
                        Collections.singletonList(new SimpleGrantedAuthority(tokenInfo.getRoles()));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(tokenInfo.getEmail(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            // Log exception
            System.err.println("Custom exception caught: " + ex.getMessage());

            // Respond with proper HTTP status and message
            response.setStatus(ex.getStatus().value());
            response.setContentType("application/json");
            response.setHeader("ERROR_CODE",ex.getMessage());
            response.getWriter().write("{\"error\": \"Custom Error\", \"message\": \"" + ex.getMessage() + "\"}");
        } catch (RuntimeException ex){
            System.err.println("run time exception caught: " + ex.getMessage());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.setHeader("ERROR_CODE",ex.getMessage());
            response.getWriter().write(ex.getMessage());

        }
        catch (Exception ex) {
            // Handle unexpected errors
            System.err.println("Unexpected exception caught: " + ex.getMessage());

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setHeader("ERROR_CODE",ex.getMessage());
            response.getWriter().write("{\"error\": \"Internal Server Error\", \"message\": \"" + ex.getMessage() + "\"}");
}
}
}
