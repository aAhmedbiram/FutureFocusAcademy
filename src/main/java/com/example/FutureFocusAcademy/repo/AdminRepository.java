package com.example.FutureFocusAcademy.repo;

import com.example.FutureFocusAcademy.document.Admin;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByUsername(@NotEmpty String username);
}
