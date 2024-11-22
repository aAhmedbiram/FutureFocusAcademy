package com.example.FutureFocusAcademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfo {
    private String email;
    private String userId;
    private String roles;
    private Date ExpiredAt;
    private Date IssuedAt;
}
