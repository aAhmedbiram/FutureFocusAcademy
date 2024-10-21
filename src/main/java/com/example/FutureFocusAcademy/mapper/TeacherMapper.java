package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.Teacher;
import com.example.FutureFocusAcademy.dto.TeacherDto;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper extends  AbstractMapper<TeacherDto, Teacher>{
    public TeacherMapper() {
        super(TeacherDto.class,Teacher.class);
    }

    @Override
    public Teacher updateToEntity(TeacherDto dto, Teacher entity) {
        if (dto.getName()!=null&&!dto.getName().isEmpty()){
            entity.setName(dto.getName());
        }
        if (dto.getEmail()!=null&&!dto.getEmail().isEmpty()){
            entity.setEmail(dto.getEmail());
        }
        if (dto.getSubject()!=null&&!dto.getSubject().isEmpty()){
            entity.setSubject(dto.getSubject());
        }
        return entity;
    }
}
