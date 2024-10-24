package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.SubUserDto;
import com.example.FutureFocusAcademy.dto.SubjectDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<SubUserDto, SubUser>{

    public UserMapper() {
        super(SubUserDto.class, SubUser.class);
    }

    @Override
    public SubUser updateToEntity(SubUserDto dto, SubUser entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setSubject(dto.getSubject());
        entity.setRole(dto.getRole());
        return entity;
    }
}
