package com.example.FutureFocusAcademy.controller;

import com.example.FutureFocusAcademy.dto.AdminDto;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Create new Admin
    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto, @RequestParam String password) {
        AdminDto createdAdmin = adminService.createAdmin(adminDto, password);
        return ResponseEntity.ok(createdAdmin);
    }

    // Get all Admins
    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Get Admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable String id) {
        AdminDto admin = adminService.getAdminById(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an Admin
    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable String id, @RequestBody AdminDto adminDto, @RequestParam(required = false) String password) {
        try {
            AdminDto updatedAdmin = adminService.updateAdmin(id, adminDto, password);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public PageResult search(@RequestParam(required = false)String name,
                             @RequestHeader(required = false,defaultValue = "0")int page,
                             @RequestHeader(required = false,defaultValue = "15")int size){
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"name"));
        return adminService.search(name,pageable);
    }

   // @PatchMapping
}
