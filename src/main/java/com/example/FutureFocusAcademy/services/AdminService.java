package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.Admin;
import com.example.FutureFocusAcademy.dto.AdminDto;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.mapper.AdminMapper;
import com.example.FutureFocusAcademy.repo.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder; // Use PasswordEncoder instead of BCryptPasswordEncoder

    @Autowired
    MongoTemplate template;

    // Method to create a new admin
    public AdminDto createAdmin(AdminDto adminDto, String rawPassword) {
        Admin admin = adminMapper.toEntity(adminDto);
        admin.setPassword(passwordEncoder.encode(rawPassword)); // Hash the password
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toDto(savedAdmin);
    }

    // Method to retrieve all admins
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    // Method to retrieve a specific admin by ID
    public AdminDto getAdminById(String id) {
        return adminRepository.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id)); // Improved error handling
    }

    // Method to update an existing admin
    public AdminDto updateAdmin(String id, AdminDto adminDto, String rawPassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id)); // Improved error handling

        // Update admin fields
        admin.setName(adminDto.getName());
        admin.setEmail(adminDto.getEmail());

        // Update password if provided
        if (rawPassword != null && !rawPassword.isEmpty()) {
            admin.setPassword(passwordEncoder.encode(rawPassword)); // Update the hashed password
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return adminMapper.toDto(updatedAdmin);
    }

    // Method to delete an admin
    public void deleteAdmin(String id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with ID: " + id); // Improved error handling
        }
        adminRepository.deleteById(id);
    }

    public PageResult search(String name, Pageable pageable){
        Query query= new Query();
        if (name!=null&&!name.isEmpty()){
            query.addCriteria(Criteria.where("name").regex(name,"i"));
        }
        Long count= template.count(query,Admin.class);
        List<AdminDto> adminDtos=template.find(query.with(pageable),Admin.class).stream().map(admin -> {
            return adminMapper.toDto(admin);
        }).toList();

        return PageResult.builder().item(adminDtos).count(count).build();
    }
}
