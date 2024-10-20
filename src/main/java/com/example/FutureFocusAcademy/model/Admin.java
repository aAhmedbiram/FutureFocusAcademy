package com.example.FutureFocusAcademy.model;

import com.example.FutureFocusAcademy.model.BaseUser;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class Admin implements BaseUser {
    // ... existing fields and methods ...

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
        return fullName;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
