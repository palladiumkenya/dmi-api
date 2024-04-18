package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String diagnosisId;
    private LocalDateTime diagnosisDate;
    private String diagnosis;
    private String system;
    private String SystemCode;
    private Boolean voided;

    public Diagnosis() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public IllnessCase getIllnessCase() {
        return illnessCase;
    }

    public void setIllnessCase(IllnessCase illnessCase) {
        this.illnessCase = illnessCase;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemCode() {
        return SystemCode;
    }

    public void setSystemCode(String systemCode) {
        SystemCode = systemCode;
    }
}
