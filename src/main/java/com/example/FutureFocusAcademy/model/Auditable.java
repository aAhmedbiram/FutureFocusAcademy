package com.example.FutureFocusAcademy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public abstract class Auditable {
    @JsonIgnore
    private LocalDateTime modifiedAt;
    @JsonIgnore
    private String modifiedBy;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private String createdBY;
}
