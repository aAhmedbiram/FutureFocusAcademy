package com.example.FutureFocusAcademy.mapper;

import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.dto.AdminDto;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDto toDto(Admin admin) {
        return new AdminDto(admin.getId(), admin.getName(), admin.getEmail());
    }

    public Admin toEntity(AdminDto adminDto) {
        return new Admin(adminDto.getId(), adminDto.getName(), adminDto.getEmail(), null);  // Password is set separately
    }
}
