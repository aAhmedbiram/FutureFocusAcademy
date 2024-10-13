package com.example.FutureFocusAcademy.repo;


import com.example.FutureFocusAcademy.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}
