package com.example.FutureFocusAcademy.repo;

import com.example.FutureFocusAcademy.document.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
}
