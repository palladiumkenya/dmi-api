package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiagnosisDto {

    @NotBlank
    private String diagnosisId;
    @ValidTimestamp
    private String diagnosisDate;
    @NotBlank
    private String diagnosis;
    private Boolean voided = false;

    public DiagnosisDto() {}

}
