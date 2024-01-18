package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RiskFactorDto {
    @Schema(description = "The unique identifier for the risk factor/comorbidity")
    @NotBlank
    private String riskFactorId;
    @Schema(description = "The reported comorbidity", example = "Diabetes")
    @NotBlank
    private String condition;
    private Boolean voided = false;
}
