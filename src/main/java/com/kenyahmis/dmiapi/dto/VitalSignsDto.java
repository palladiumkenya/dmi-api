package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VitalSignsDto {
    @NotBlank
    private String vitalSignId;
    private Double temperature;
    private String temperatureMode;
    private Integer respiratoryRate;
    private Integer oxygenSaturation;
    private String oxygenSaturationMode;
    @ValidTimestamp
    private String vitalSignDate;
    private Boolean voided = false;
}
