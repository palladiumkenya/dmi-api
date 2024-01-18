package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class DiagnosisDto {

    @Schema(description = "The diagnosis unique identifier within the submitting EMR")
    @NotBlank
    private String diagnosisId;
    @Schema(description = "The timestamp for when the diagnosis was made")
    @ValidTimestamp
    private String diagnosisDate;
    @NotBlank
    private String diagnosis;
    @Schema(description = "A global library of medical diagnosis terms", example = "ICD-10")
    private String system;
    @Schema(description = "The code for a diagnosis as defined by the system library", example = "J00-J06")
    private String systemCode;
    private Boolean voided = false;
    public DiagnosisDto() {}
}
