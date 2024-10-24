package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.Credentials;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.model.TokenInfo;
import com.example.FutureFocusAcademy.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FutureFocusAcademy.repo.UserRepository; // Use the correct repository

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
     UserRepository userRepository; // Replace with your user repository

    @Autowired
    MongoTemplate template;



    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SubUser user = userRepository.findByEmail(email); // Ensure this method works in your repository
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user); // Make sure UserDetailsImpl accepts BaseUser and handles it correctly
    }

    public Boolean isValid(TokenInfo tokenInfo){
        Query query= new Query();
        query.addCriteria(Criteria.where("username").is(tokenInfo.getUsername()));
        query.addCriteria(Criteria.where("userId").is(tokenInfo.getUserId()));
        query.addCriteria(Criteria.where("roles").is(tokenInfo.getRoles()));

        return template.exists(query, SubUser.class);
    }

    public String login(Credentials credentials){
        SubUser user;
        try{
            user=userRepository.findByEmail(credentials.getEmail());
        }catch (Exception ex){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword() )){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        return jwtUtils.generate(user);
    }
}
