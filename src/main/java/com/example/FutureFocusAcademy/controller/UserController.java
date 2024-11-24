package com.example.FutureFocusAcademy.controller;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.Credentials;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.SubUserDto;
import com.example.FutureFocusAcademy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/admin")
    public String create(@RequestBody SubUserDto dto){
        return service.save(dto);
    }
    @DeleteMapping("/{id}")
    public void  delete(@PathVariable String id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody SubUserDto dto)
    {
        service.update(id, dto);
    }
    @GetMapping("/{email}")
    public SubUserDto getByEmail(@PathVariable String email){
        return service.getByEmail(email);
    }
    @GetMapping("/search")
    public PageResult search(@RequestParam(required = false)String name,
                             @RequestParam(required = false)String email,
                             @RequestParam(required = false)String roles,
                             @RequestHeader(required = false,defaultValue = "0")int page,
                             @RequestHeader(required = false,defaultValue = "15")int size){
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"name"));
        return service.search(name,email,roles,pageable);
    }
    @PatchMapping
    public SubUserDto miniUpdate(@PathVariable String id,@RequestBody SubUser subUser){
        return service.miniUpdate(id, subUser);
    }
    @PostMapping("/login")
    public  String login(@RequestBody Credentials credentials){
        return service.login(credentials);
    }
}
