package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lab")
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RespiratoryIllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String orderId;
    private String result;
    private LocalDateTime labDate;
    private Boolean voided;

    public Lab() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RespiratoryIllnessCase getIllnessCase() {
        return illnessCase;
    }

    public void setIllnessCase(RespiratoryIllnessCase illnessCase) {
        this.illnessCase = illnessCase;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getLabDate() {
        return labDate;
    }

    public void setLabDate(LocalDateTime labDate) {
        this.labDate = labDate;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
}
