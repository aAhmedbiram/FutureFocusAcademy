package com.example.FutureFocusAcademy.dto;

import com.example.FutureFocusAcademy.model.Grades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private String id;
    private String name;
    private String email;
    private String course;
//    private Grades grades;



}
