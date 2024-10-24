package com.example.FutureFocusAcademy.dto;

import com.example.FutureFocusAcademy.document.SubUser;
import com.example.FutureFocusAcademy.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubUserDto extends Auditable {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private SubjectDto subjectDto;
}
