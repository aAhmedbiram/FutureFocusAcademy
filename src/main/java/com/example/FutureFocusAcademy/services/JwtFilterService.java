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
        final String authorization=request.getHeader("Authorization");

        if (authorization !=null && authorization.startsWith("Bearer ")){
            String token=authorization.substring(7);

            if (!jwtUtils.isValid(token)){
                throw new CustomException("invalid token", HttpStatus.UNAUTHORIZED);
            }

            TokenInfo tokenInfo=jwtUtils.extractInfo(token);
            if (userDetailsService.isValid(tokenInfo)){
                throw new CustomException("invalid token", HttpStatus.UNAUTHORIZED);
            }
            List<GrantedAuthority> authirities= Collections.singletonList(new SimpleGrantedAuthority(tokenInfo.getRoles()));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(tokenInfo.getEmail() , null ,authirities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);


}
}
