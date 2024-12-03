package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.document.Subject;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.SubUserDto;
import com.example.FutureFocusAcademy.dto.SubjectDto;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.mapper.SubjectMapper;

import com.example.FutureFocusAcademy.repo.SubjectReposatory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    SubjectMapper mapper;
    @Autowired
    SubjectReposatory reposatory;
    @Autowired
    MongoTemplate template;

    public String save(SubjectDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(dto.getName()));
        if (template.exists(query, Subject.class)){
            throw new  CustomException ("subject.exsist", HttpStatus.NOT_ACCEPTABLE);
        }
        return template.save(mapper.toEntity(dto)).getId();
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        if (!template.exists(query,Subject.class)){
            throw new CustomException("subject.delete",HttpStatus.NOT_FOUND);
        }
        template.remove(query,Subject.class);
    }

    public void update(String id, SubjectDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(dto.getName()));
        query.addCriteria(Criteria.where("_id").ne(id));
        if (template.exists(query,Subject.class)){
            throw new CustomException("subject.exist",HttpStatus.NOT_ACCEPTABLE);
        }
        query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Subject subject= template.findOne(query,Subject.class);
        template.save(mapper.updateToEntity(dto,subject));
    }

    public SubjectDto getByName(String name) {
        Query query= new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mapper.toDto(template.findOne(query,Subject.class));
    }

    public PageResult search(String name, SubUser user, Pageable pageable) {
        Query query =new Query();
        if (name  != null && name.isEmpty()){
            query.addCriteria(Criteria.where("name").regex("i"));
        }
        if (user != null && user.getName().isEmpty()){
            query.addCriteria(Criteria.where("user").regex("i"));
        }
        List<SubjectDto> subjectDtos= template.find(query.with(pageable),Subject.class).stream().map(subject ->{
            return mapper.toDto(subject);
    }).collect(Collectors.toList());
        Long count =template.count(query,Subject.class);
        List<SubjectDto> subj = template.find(query.with(pageable),Subject.class).stream().map(mapper::toDto).toList();
        return PageResult.builder().item(subj).count(count).build();
}
}
