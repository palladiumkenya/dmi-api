package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String patientUniqueId;
    private String nupi;
    private String sex;
    private String address;
    private String county;
    private String subCounty;
    private LocalDateTime dateOfBirth;
}
