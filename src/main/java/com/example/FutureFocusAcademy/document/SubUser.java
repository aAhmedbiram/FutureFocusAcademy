package com.example.FutureFocusAcademy.document;

import com.example.FutureFocusAcademy.model.Auditable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="user")
@Builder
public class SubUser extends Auditable {
    @Id
    private String id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;
    private Subject subject;
    private HashMap<String,Object> student;
}
