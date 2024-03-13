package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "emr")
public class Emr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String emrName;
    private Boolean voided;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Emr() {
    }
}
