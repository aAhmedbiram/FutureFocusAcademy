package com.example.FutureFocusAcademy.repo;


import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.document.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    Student findStudentByUsername(String username);

    Teacher findTeacherByUsername(String username);

    Admin findAdminByUsername(String username);
}
