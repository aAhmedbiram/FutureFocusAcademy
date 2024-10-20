package com.example.FutureFocusAcademy.document;

import com.example.FutureFocusAcademy.model.BaseUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Document(collection = "students")
public class Student implements BaseUser {
    // Getters and setters
    @Getter
    private String id; // Assuming you have an ID field
    // Added setter for username
    private String username; // Added this field
    // Added setter for password
    private String password; // Added this field for authentication
    // Added setter for full name
    private String fullName; // Added for full name
    private String email;
    @Getter
    private String course;

    // Default constructor
    public Student() {
    }

    @Override
    public String getUsername() {
        return username; // Now correctly implemented
    }

    @Override
    public String getPassword() {
        return password; // Now correctly implemented
    }

    @Override
    public String getFullName() {
        return fullName; // Changed to return fullName
    }

    @Override
    public String getEmail() {
        return email; // Now correctly implemented
    }

}
