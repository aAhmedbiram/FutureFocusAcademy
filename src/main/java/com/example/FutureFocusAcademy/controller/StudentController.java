package com.example.FutureFocusAcademy.controller;

import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.StudentDTO;
import com.example.FutureFocusAcademy.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/search")
    public PageResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String course,
                             @RequestHeader(required = false,defaultValue = "0") int page,
                             @RequestHeader(required = false,defaultValue = "15") int size){
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"name"));
        return studentService.search(name,course,pageable);
    }
}
