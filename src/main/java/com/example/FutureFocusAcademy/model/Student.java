package com.example.FutureFocusAcademy.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student implements BaseUser {

    private final String username;
    private final String password;
//    private String fullName;
    private final String email;

    // Constructor
    public Student(String username, String password, String fullName, String email) {
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

//    @Override
//    public String getFullName() {
//        return fullName;
//    }

    @Override
    public String getEmail() {
        return email;
    }

    // Optionally, you can override toString() method for better logging/debugging
    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
//                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
