package com.example.FutureFocusAcademy.document;

import com.example.FutureFocusAcademy.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "admin")
public class Admin extends Auditable {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}

