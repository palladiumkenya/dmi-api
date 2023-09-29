package com.kenyahmis.dmiapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IllnessCaseDto {

    @NotEmpty
    private String patientUniqueId;
    private String nupi;
    @NotEmpty
    private String caseUniqueId;
    @NotEmpty
    private String hospitalIdNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime interviewDate;
    private LocalDate dateOfBirth;
    private Integer ageInMonths;
    private Integer ageInYears;
    private String sex;
    private String address;
    private Double temperature;
    private Boolean voided;
    private List<ComplaintDto> complaintDtoList = new ArrayList<>();
    private List<LabDto> labDtoList = new ArrayList<>();
    private List<DiagnosisDto> diagnosis = new ArrayList<>();
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
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

    public IllnessCaseDto() {
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

    public String getCaseUniqueId() {
        return caseUniqueId;
    }

    public void setCaseUniqueId(String caseUniqueId) {
        this.caseUniqueId = caseUniqueId;
    }

    public String getHospitalIdNumber() {
        return hospitalIdNumber;
    }

    public void setHospitalIdNumber(String hospitalIdNumber) {
        this.hospitalIdNumber = hospitalIdNumber;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public List<ComplaintDto> getComplaintDtoList() {
        return complaintDtoList;
    }

    public void setComplaintDtoList(List<ComplaintDto> complaintDtoList) {
        this.complaintDtoList = complaintDtoList;
    }

    public List<LabDto> getLabDtoList() {
        return labDtoList;
    }

    public void setLabDtoList(List<LabDto> labDtoList) {
        this.labDtoList = labDtoList;
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

    public List<DiagnosisDto> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<DiagnosisDto> diagnosis) {
        this.diagnosis = diagnosis;
    }
}
