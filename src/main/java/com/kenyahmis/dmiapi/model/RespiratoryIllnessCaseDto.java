package com.kenyahmis.dmiapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RespiratoryIllnessCaseDto {

    private String patientUniqueId;
    private String visitUniqueId;
    private String hospitalIdNumber;
    private LocalDate interviewDate;
    private Boolean verbalConsentDone;
    private LocalDate dateOfBirth;
    private Integer infantAge;
    private Integer age;
    private String sex;
    private String address;
    private LocalDate illnessOnsetDate;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    private Double temperature;
    private String[] infantComplaints;
    private String[] complaints;
    private Integer durationOfComplaints;
    private String county;
    private String subCounty;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    public RespiratoryIllnessCaseDto() {
    }

    public RespiratoryIllnessCaseDto(String patientUniqueId, String sex, String county, String subCounty) {
        this.patientUniqueId = patientUniqueId;
        this.sex = sex;
        this.county = county;
        this.subCounty = subCounty;
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

    public String getHospitalIdNumber() {
        return hospitalIdNumber;
    }

    public void setHospitalIdNumber(String hospitalIdNumber) {
        this.hospitalIdNumber = hospitalIdNumber;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Boolean getVerbalConsentDone() {
        return verbalConsentDone;
    }

    public void setVerbalConsentDone(Boolean verbalConsentDone) {
        this.verbalConsentDone = verbalConsentDone;
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

    @Override
    public String toString() {
        return "RespiratoryIllnessCaseDto{" +
                "patientUniqueId='" + patientUniqueId + '\'' +
                ", visitUniqueId='" + visitUniqueId + '\'' +
                ", hospitalIdNumber='" + hospitalIdNumber + '\'' +
                ", interviewDate=" + interviewDate +
                ", verbalConsentDone=" + verbalConsentDone +
                ", dateOfBirth=" + dateOfBirth +
                ", infantAge=" + infantAge +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", illnessOnsetDate=" + illnessOnsetDate +
                ", admissionDate=" + admissionDate +
                ", outpatientDate=" + outpatientDate +
                ", temperature=" + temperature +
                ", infantComplaints=" + Arrays.toString(infantComplaints) +
                ", complaints=" + Arrays.toString(complaints) +
                ", durationOfComplaints=" + durationOfComplaints +
                ", county='" + county + '\'' +
                ", subCounty='" + subCounty + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
