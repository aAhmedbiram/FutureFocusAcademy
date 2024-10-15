package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.dto.AdminDto;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper extends AbstractMapper<AdminDto, Admin> {

    public AdminMapper() {
        super(AdminDto.class, Admin.class);
    }

    @Override
    public Admin updateToEntity(AdminDto dto, Admin entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        // Update additional fields as needed
        return entity;  // Return the updated entity
    }
}
