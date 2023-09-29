package com.kenyahmis.dmiapi.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class ComplaintDto {
    @NotEmpty
    private String complaintId;
    @NotEmpty
    private String complaint;
    private Boolean voided = false;
    private LocalDate onsetDate;
    private Integer duration;

    public ComplaintDto() {
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public LocalDate getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(LocalDate onsetDate) {
        this.onsetDate = onsetDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
