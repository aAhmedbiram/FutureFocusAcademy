package com.example.FutureFocusAcademy.document;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("/subject")
public class Subject {
    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String studentGrade;
    @NotEmpty
    private Student student;
    @NotEmpty
    private Teacher teacher;
}
