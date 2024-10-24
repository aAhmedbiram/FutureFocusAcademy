package com.example.FutureFocusAcademy.repo;

import com.example.FutureFocusAcademy.document.SubUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<SubUser, String> {
    SubUser findByUsername(String username);
}
