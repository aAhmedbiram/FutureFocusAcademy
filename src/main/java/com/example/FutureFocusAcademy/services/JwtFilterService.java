package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.model.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterService extends OncePerRequestFilter {
    @Autowired
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

          TokenInfo tokenInfo=jwtUtils.extract(token);
          if (userDetailsService.isValid(tokenInfo)){
              throw new CustomException("invalid token", HttpStatus.UNAUTHORIZED);
          }
          filterChain.doFilter(request,response);
      }


    }
}
