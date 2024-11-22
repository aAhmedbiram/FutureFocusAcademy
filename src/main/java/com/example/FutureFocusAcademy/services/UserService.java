package com.example.FutureFocusAcademy.services;

import com.example.FutureFocusAcademy.Utils.JwtUtils;
import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.dto.Credentials;
import com.example.FutureFocusAcademy.dto.PageResult;
import com.example.FutureFocusAcademy.dto.SubUserDto;
import com.example.FutureFocusAcademy.exceptions.CustomException;
import com.example.FutureFocusAcademy.mapper.UserMapper;
import com.example.FutureFocusAcademy.repo.UserRepository;
import jakarta.annotation.PostConstruct;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    MongoTemplate template;
    @Autowired
    PasswordEncoder password;
    @Autowired
    UserMapper mapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostConstruct
    public void init(){
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is("ADMIN"));
        if (!template.exists(query, SubUser.class)){
            template.save(SubUser.builder().role("ADMIN").password(passwordEncoder.encode("admin")).email("admin").build(),"user");
        }
    }
    public String save(SubUserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
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
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
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
            query.addCriteria(Criteria.where("name").regex(name,"i"));
        if (email!=null)
            query.addCriteria(Criteria.where("email").regex(email,"i"));
        List<SubUserDto> users= template.find(query.with(pageable),SubUser.class).stream().map(user->{
            return mapper.toDto(user);

        }).collect(Collectors.toList());
        Long count=template.count(query,SubUser.class);
        List <SubUserDto> subUserDtos = template.find(query,SubUser.class).stream().map(subUser -> {
            return mapper.toDto(subUser);
        }).collect(Collectors.toList());
        return PageResult.builder().item(subUserDtos).count(count).build();
    }

    public SubUserDto miniUpdate(String id, SubUser subUser) {
        Optional<SubUser> subUser1=repository.findById(id);
        subUser1.get().getSubject().setStudentGrade(subUser.getSubject().getStudentGrade());
        return mapper.toDto(subUser1.get());
    }
    public String login(Credentials credentials){
        SubUser user;
        try{
            user=repository.findByEmail(credentials.getEmail());
        }catch (Exception ex){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword() )){
            throw new CustomException(" credentials invalid", HttpStatus.UNAUTHORIZED);
        }
        return jwtUtils.generate(user);
    }
}

