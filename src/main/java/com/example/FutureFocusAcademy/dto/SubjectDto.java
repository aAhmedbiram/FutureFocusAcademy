package com.example.FutureFocusAcademy.dto;

import com.example.FutureFocusAcademy.document.Student;
import com.example.FutureFocusAcademy.document.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private String id;
    private String name;
    private String studentGrade;
    private Student student;
    private Teacher teacher;
}
