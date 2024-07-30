package com.kenyahmis.dmiapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "vital_sign")
public class VitalSign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private IllnessCase illnessCase;
    private LocalDateTime vitalSignDate;
    private String vitalSignId;
    private Double temperature;
    private String temperatureMode;
    private Integer respiratoryRate;
    private Integer oxygenSaturation;
    private String oxygenSaturationMode;
    private Boolean voided;
}
