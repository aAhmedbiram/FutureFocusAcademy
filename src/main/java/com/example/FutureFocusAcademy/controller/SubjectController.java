package com.example.FutureFocusAcademy.controller;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.SubjectDto;
import com.example.FutureFocusAcademy.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    @Autowired
    SubjectService service;
    @PostMapping("/teacher")
    public String create(@RequestBody  SubjectDto dto){
        return service.save(dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable String id ,@RequestBody SubjectDto dto){
        service.update(id,dto);
    }
    @GetMapping("/{name}")
    public SubjectDto getByName(@PathVariable String name){
        return service.getByName(name);
    }
    @GetMapping("/search")
    public PageResult search(@RequestParam(required = false)String name,
                             @RequestParam(required = false)SubUser user,
                             @RequestHeader(required = false,defaultValue = "0")int page,
                             @RequestHeader(required = false,defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"name"));
        return service.search(name,user,pageable);
    }

}
