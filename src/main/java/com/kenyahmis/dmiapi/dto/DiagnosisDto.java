package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@Schema(name = "diagnosis")
public class DiagnosisDto {

    @Schema(description = "The diagnosis unique identifier within the submitting EMR", example = "8643226")
    @NotBlank
    private String diagnosisId;
    @Schema(description = "The timestamp for when the diagnosis was made", example = "2024-01-15 06:09:03")
    @ValidTimestamp
    private String diagnosisDate;
    @Schema(example = "Cholera")
    @NotBlank
    private String diagnosis;
    @Schema(description = "A global library of medical diagnosis terms", example = "ICD-10")
    private String system;
    @Schema(description = "The code for a diagnosis as defined by the system library", example = "J00-J06")
    private String systemCode;
    @Schema(example = "false")
    private Boolean voided = false;
    public DiagnosisDto() {}
}
