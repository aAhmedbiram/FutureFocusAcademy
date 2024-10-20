package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.dto.StudentDTO;
import com.example.FutureFocusAcademy.repo.StudentRepository;
import com.example.FutureFocusAcademy.mapper.StudentMapper;
import com.example.FutureFocusAcademy.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));
    }

    public Student createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        return studentRepository.save(student);
    }

    public Student updateStudent(String id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));

        // Use the mapper to update fields
        studentMapper.updateToEntity(studentDTO, existingStudent);
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}
