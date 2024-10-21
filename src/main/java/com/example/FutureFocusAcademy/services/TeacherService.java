package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.Teacher;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.TeacherDto;
import com.example.FutureFocusAcademy.mapper.TeacherMapper;
import com.example.FutureFocusAcademy.repo.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    MongoTemplate template;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(String id, Teacher updatedTeacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(id);
        if (existingTeacher.isPresent()) {
            Teacher teacher = existingTeacher.get();
            teacher.setName(updatedTeacher.getName());
            teacher.setEmail(updatedTeacher.getEmail());
            teacher.setSubject(updatedTeacher.getSubject());
            return teacherRepository.save(teacher);
        } else {
            return null; // Or handle with a custom exception
        }
    }

    public void deleteTeacher(String id) {
        teacherRepository.deleteById(id);
    }

    public PageResult search(String name, String course, Pageable pageable){
        Query query= new Query();
        if (name!=null&&!name.isEmpty()){
            query.addCriteria(Criteria.where("name").regex(name,"i"));
        }
        if (course!=null&&!course.isEmpty()){
            query.addCriteria(Criteria.where("course").regex(course,"i"));
        }
        Long count= template.count(query,Teacher.class);
        List<TeacherDto> teacherDtos=template.find(query.with(pageable),Teacher.class).stream().map(teacher -> {
            return teacherMapper.toDto(teacher);
        }).toList();
        return PageResult.builder().item(teacherDtos).count(count).build();
    }
}
