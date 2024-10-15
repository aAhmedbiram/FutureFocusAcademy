package com.example.FutureFocusAcademy.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "teachers")
public class Teacher {

    @Id
    private String id;
    private String name;
    private String email;
    private String subject;
}






