package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.Subject;
import com.example.FutureFocusAcademy.dto.SubjectDto;
import com.example.FutureFocusAcademy.model.Grades;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper extends AbstractMapper<SubjectDto, Subject> {
    public SubjectMapper() {
        super(SubjectDto.class, Subject.class);
    }

    @Override
    public Subject updateToEntity(SubjectDto dto, Subject entity) {
        entity.setName(dto.getName());
        entity.setUser(dto.getUser());
        entity.setStudentGrade(Grades.valueOf(dto.getStudentGrade()));
        return entity;
    }

}
