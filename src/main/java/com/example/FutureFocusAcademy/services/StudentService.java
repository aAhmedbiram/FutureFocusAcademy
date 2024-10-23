package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.document.Subject;
import com.example.FutureFocusAcademy.document.Teacher;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.StudentDTO;
import com.example.FutureFocusAcademy.repo.StudentRepository;
import com.example.FutureFocusAcademy.mapper.StudentMapper;
import com.example.FutureFocusAcademy.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    MongoTemplate template;

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

    public PageResult search(String name, String course, Pageable pageable){
        Query query=new Query();
        if (name!=null&&!name.isEmpty()){
            query.addCriteria(Criteria.where("name").regex(name,"i"));
        }
        if (course!=null&&!course.isEmpty()){
            query.addCriteria(Criteria.where("course").regex(course,"i"));
        }
        Long count = template.count(query,Student.class);
        List<StudentDTO> studentDTOS=template.find(query.with(pageable),Student.class).stream().map(student -> {
            return studentMapper.toDto(student);
        }).toList();
        return PageResult.builder().item(studentDTOS).count(count).build();
    }

    public Student miniUpdate(Subject subject) {
        subject.getStudent();
        Teacher teacher=new Teacher();
        teacher.setSubject(subject.getStudentGrade());
        return miniUpdate(subject);
    }
}
