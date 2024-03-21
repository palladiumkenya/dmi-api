package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "riskFactors")
public class RiskFactorDto {
    @Schema(description = "The unique identifier for the risk factor/comorbidity", example = "99876")
    @NotBlank
    private String riskFactorId;
    @Schema(description = "The reported comorbidity", example = "Diabetes")
    @NotBlank
    private String condition;
    @Schema(example = "false")
    private Boolean voided = false;
}
