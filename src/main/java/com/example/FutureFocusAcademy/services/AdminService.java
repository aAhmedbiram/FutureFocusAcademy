package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.dto.AdminDto;
import com.example.FutureFocusAcademy.repo.AdminRepository;
import com.example.FutureFocusAcademy.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public AdminDto createAdmin(AdminDto adminDto, String rawPassword) {
        Admin admin = adminMapper.toEntity(adminDto);
        admin.setPassword(passwordEncoder.encode(rawPassword)); // Hash the password
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toDto(savedAdmin);
    }


    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }


    public AdminDto getAdminById(String id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        return adminOptional.map(adminMapper::toDto).orElse(null);
    }


    public AdminDto updateAdmin(String id, AdminDto adminDto, String rawPassword) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setName(adminDto.getName());
            admin.setEmail(adminDto.getEmail());
            if (rawPassword != null && !rawPassword.isEmpty()) {
                admin.setPassword(passwordEncoder.encode(rawPassword)); // Update hashed password
            }
            Admin updatedAdmin = adminRepository.save(admin);
            return adminMapper.toDto(updatedAdmin);
        } else {
            throw new RuntimeException("Admin not found");
        }
    }


    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }
}
