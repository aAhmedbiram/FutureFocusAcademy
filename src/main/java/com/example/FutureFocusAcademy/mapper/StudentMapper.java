package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.dto.StudentDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper extends AbstractMapper<StudentDTO, Student> {
    public StudentMapper() {
        super(StudentDTO.class, Student.class);
    }

    @Override
    public Student updateToEntity(StudentDTO dto, Student entity) {
        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            entity.setFullName(dto.getFullName()); // Change to setFullName
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getCourse() != null && !dto.getCourse().isEmpty()) {
            entity.setCourse(dto.getCourse());
        }
        return entity;
    }
}
