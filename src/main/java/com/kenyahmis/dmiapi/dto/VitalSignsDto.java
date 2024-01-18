package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VitalSignsDto {
    @Schema(description = "The unique identifier for the vital sign entry within the submitting EMR")
    @NotBlank
    private String vitalSignId;
    private Double temperature;
    @Schema(description = "The mode of temperature reading. Options include: 'Oral', 'Auxiliary', 'Forehead thermometer gun'")
    private String temperatureMode;
    private Integer respiratoryRate;
    private Integer oxygenSaturation;
    @Schema(description = "The mode of oxygen saturation reading. Options include: 'Room air On supplemental oxygen','Room air On supplemental oxygen'")
    private String oxygenSaturationMode;
    @Schema(description = "The timestamp for when the vitals were collected", example = "2024-01-15 06:09:03")
    @ValidTimestamp
    private String vitalSignDate;
    private Boolean voided = false;
}
