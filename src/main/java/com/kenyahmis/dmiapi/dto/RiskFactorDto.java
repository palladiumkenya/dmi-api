package com.kenyahmis.dmiapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RiskFactorDto {
    @NotBlank
    private String riskFactorId;
    @NotBlank
    private String condition;
    private Boolean voided = false;
}
