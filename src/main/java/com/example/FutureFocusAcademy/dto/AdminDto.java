package com.example.FutureFocusAcademy.dto;

import com.example.FutureFocusAcademy.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto extends Auditable {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}
