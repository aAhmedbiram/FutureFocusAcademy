package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.Credentials;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.model.TokenInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SubUser user = userRepository.findByEmail(username); // Ensure this method works in your repository
        return User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole()).authorities(user.getRole()).build();
    }

    public Boolean isValid(TokenInfo tokenInfo){
        Query query= new Query();
        query.addCriteria(Criteria.where("email").is(tokenInfo.getEmail()));
        query.addCriteria(Criteria.where("_id").is(tokenInfo.getUserId()));
        query.addCriteria(Criteria.where("role").is(tokenInfo.getRoles()));

        if (!template.exists(query,SubUser.class)){
            return false;
        }
        return true;
    }


}
