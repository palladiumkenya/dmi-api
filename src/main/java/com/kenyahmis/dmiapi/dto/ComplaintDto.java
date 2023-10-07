package com.kenyahmis.dmiapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ComplaintDto {
    @NotBlank
    private String complaintId;
    @NotBlank
    private String complaint;
    private Boolean voided = false;
    private LocalDate onsetDate;
    private Integer duration;

    public ComplaintDto() {
    }
}
