package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.SubUserDto;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.mapper.UserMapper;
import com.example.FutureFocusAcademy.repo.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    MongoTemplate template;
    @Autowired
    PasswordEncoder password;
    @Autowired
    UserMapper mapper;

    public void init(SubUser subUser){
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is("ADMIN"));
        if (!template.exists(query, SubUser.class)){
            template.save(subUser, builder().build().toString());
        }
    }
    public String save(SubUserDto dto) {
        Query query =new Query();
        query.addCriteria(Criteria.where("_id").is(dto.getId()));
        if (template.exists(query,SubUser.class))
            throw new CustomException("user is order exist",HttpStatus.CREATED);
//        dto.setCreatedAt();
        return template.save(mapper.toEntity(dto)).getId();
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        if (!template.exists(query,SubUser.class))
            throw new CustomException("user is not exist",HttpStatus.NOT_FOUND);
        template.remove(query,SubUser.class);
    }

    public void update(String id, SubUserDto dto) {
        Query query =new Query();
        query.addCriteria(Criteria.where("email").is(dto.getEmail()));
        query.addCriteria(Criteria.where("_id").is(id));
        if (template.exists(query,SubUser.class))
            throw new RuntimeException("user is already exist");
        query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        SubUser user = template.findOne(query,SubUser.class);
        template.save(mapper.updateToEntity(dto,user));
    }

    public SubUserDto getByEmail(String email) {
        Query query= new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mapper.toDto(template.findOne(query,SubUser.class));
    }

    public PageResult search(String name, String email, Pageable pageable) {
        Query query = new Query();
        if (name!= null)
            query.addCriteria(Criteria.where("name").regex("1"));
        if (email!=null)
            query.addCriteria(Criteria.where("email").regex("1"));
        List<SubUserDto> users= template.find(query.with(pageable),SubUser.class).stream().map(user->{
            return mapper.toDto(user);

        }).collect(Collectors.toList());
        Long count=template.count(query,SubUser.class);
        List SubUser = List.of();
        return PageRequest.builder().item(SubUser).count(count).build();
    }

}

