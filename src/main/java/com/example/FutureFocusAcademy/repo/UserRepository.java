package com.example.FutureFocusAcademy.repo;

import com.example.FutureFocusAcademy.model.BaseUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<BaseUser, String> {
    BaseUser findByUsername(String username);
}
