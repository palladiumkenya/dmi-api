package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "respiratory_illness_case")
public class RespiratoryIllnessCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String patientUniqueId;
    private String visitUniqueId;
    private String mflCode;
    private LocalDate interviewDate;
    private Boolean verbalConsentingDone;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    private Integer infantAge;
    private Integer age;
    private String sex;
    private String address;
    private LocalDate illnessOnsetDate;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    private Double temperature;
    @Column(columnDefinition = "varchar[]", name = "infant_complaints")
    @Type(value = com.kenyahmis.dmiapi.model.CustomStringArrayType.class)
    private String[] infantComplaints;
    @Column(columnDefinition = "varchar[]", name = "complaints")
    @Type(value = com.kenyahmis.dmiapi.model.CustomStringArrayType.class)
    private String[] complaints;
    private Integer durationOfComplaints;
    private String county;
    private String subCounty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loadDate;

    public RespiratoryIllnessCase() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPatientUniqueId() {
        return patientUniqueId;
    }

    public void setPatientUniqueId(String patientUniqueId) {
        this.patientUniqueId = patientUniqueId;
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

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Boolean getVerbalConsentingDone() {
        return verbalConsentingDone;
    }

    public void setVerbalConsentingDone(Boolean verbalConsentDone) {
        this.verbalConsentingDone = verbalConsentDone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getInfantAge() {
        return infantAge;
    }

    public void setInfantAge(Integer infantAge) {
        this.infantAge = infantAge;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public LocalDate getIllnessOnsetDate() {
        return illnessOnsetDate;
    }

    public void setIllnessOnsetDate(LocalDate illnessOnsetDate) {
        this.illnessOnsetDate = illnessOnsetDate;
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

    public String[] getInfantComplaints() {
        return infantComplaints;
    }

    public void setInfantComplaints(String[] infantComplaints) {
        this.infantComplaints = infantComplaints;
    }

    public String[] getComplaints() {
        return complaints;
    }

    public void setComplaints(String[] complaints) {
        this.complaints = complaints;
    }

    public Integer getDurationOfComplaints() {
        return durationOfComplaints;
    }

    public void setDurationOfComplaints(Integer durationOfComplaints) {
        this.durationOfComplaints = durationOfComplaints;
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
}
