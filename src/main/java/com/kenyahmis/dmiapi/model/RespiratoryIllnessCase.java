package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "respiratory_illness_case")
public class RespiratoryIllnessCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "batchId", referencedColumnName = "id")
    private UUID batchId;
    private String patientUniqueId;
    private String nupi;
    private String visitUniqueId;
    private String mflCode;
    private LocalDateTime interviewDate;
    private Boolean verbalConsentingDone;
    private LocalDate dateOfBirth;
    private Integer ageInMonths;
    private Integer ageInYears;
    private String sex;
    private String address;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    private Double temperature;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Complaint> complaints;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Lab> labs;
    private String county;
    private String subCounty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loadDate;
    private Boolean voided;
    public RespiratoryIllnessCase() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    public String getPatientUniqueId() {
        return patientUniqueId;
    }

    public void setPatientUniqueId(String patientUniqueId) {
        this.patientUniqueId = patientUniqueId;
    }

    public String getNupi() {
        return nupi;
    }

    public void setNupi(String nupi) {
        this.nupi = nupi;
    }

    public String getVisitUniqueId() {
        return visitUniqueId;
    }

    public void setVisitUniqueId(String visitUniqueId) {
        this.visitUniqueId = visitUniqueId;
    }

    public String getMflCode() {
        return mflCode;
    }

    public void setMflCode(String mflCode) {
        this.mflCode = mflCode;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Boolean getVerbalConsentingDone() {
        return verbalConsentingDone;
    }

    public void setVerbalConsentingDone(Boolean verbalConsentingDone) {
        this.verbalConsentingDone = verbalConsentingDone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAgeInMonths() {
        return ageInMonths;
    }

    public void setAgeInMonths(Integer ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    public Integer getAgeInYears() {
        return ageInYears;
    }

    public void setAgeInYears(Integer ageInYears) {
        this.ageInYears = ageInYears;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getOutpatientDate() {
        return outpatientDate;
    }

    public void setOutpatientDate(LocalDate outpatientDate) {
        this.outpatientDate = outpatientDate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Lab> getLabs() {
        return labs;
    }

    public void setLabs(List<Lab> labs) {
        this.labs = labs;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(LocalDateTime loadDate) {
        this.loadDate = loadDate;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
}
