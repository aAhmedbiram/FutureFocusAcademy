package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.dto.Credentials;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.model.TokenInfo;
import com.example.FutureFocusAcademy.repo.AdminRepository;
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

import com.example.FutureFocusAcademy.model.BaseUser;
import com.example.FutureFocusAcademy.model.UserDetailsImpl;
import com.example.FutureFocusAcademy.repo.UserRepository; // Use the correct repository

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Replace with your user repository

    @Autowired
    MongoTemplate template;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = userRepository.findByUsername(username); // Ensure this method works in your repository
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

        return template.exists(query, Admin.class);
    }

    public String login(Credentials credentials){
        Admin admin;
        try{
            admin=adminRepository.findByUsername(credentials.getUsername());
        }catch (Exception ex){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(credentials.getPassword(), admin.getPassword() )){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        return jwtUtils.generate(admin);
    }
}
