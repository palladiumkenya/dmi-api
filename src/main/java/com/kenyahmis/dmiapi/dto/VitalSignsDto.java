package com.kenyahmis.dmiapi.dto;

import lombok.Data;

@Data
public class VitalSignsDto {
    private String vitalSignId;
    private Double temperature;
    private String temperatureMode;
    private Integer respiratoryRate;
    private Integer oxygenSaturation;
    private String oxygenSaturationMode;
    private Boolean voided = false;
}
