package com.example.FutureFocusAcademy.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class Admin implements BaseUser {

    private final String username;
    private final String password;
//    private String fullName;
    private final String email;

    // Constructor
    public Admin(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
//        this.fullName = fullName;
        this.email = email;
    }

    // Getters and setters
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFullName() {
        return "";
    }

//    @Override
//    public String getFullName() {
//        return fullName;
//    }

    @Override
    public String getEmail() {
        return email;
    }
}
