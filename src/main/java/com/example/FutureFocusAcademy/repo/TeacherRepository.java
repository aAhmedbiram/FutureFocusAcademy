package com.example.FutureFocusAcademy.repo;

import com.example.FutureFocusAcademy.document.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
}
