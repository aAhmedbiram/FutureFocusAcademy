package com.example.FutureFocusAcademy.document;

import com.example.FutureFocusAcademy.model.Grades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private String course;
    private Grades grade;

}
