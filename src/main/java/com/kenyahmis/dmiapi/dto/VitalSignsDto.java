package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "vitalSigns")
public class VitalSignsDto {
    @Schema(description = "The unique identifier for the vital sign entry within the submitting EMR", example = "765789")
    @NotBlank
    private String vitalSignId;
    @Schema(example = "36.5")
    private Double temperature;
    @Schema(description = "The mode of temperature reading. Options include: 'Oral', 'Auxiliary', 'Forehead thermometer gun'", example = "Auxiliary")
    private String temperatureMode;
    @Schema(example = "25")
    private Integer respiratoryRate;
    @Schema(example = "98")
    private Integer oxygenSaturation;
    @Schema(description = "The mode of oxygen saturation reading. Options include: 'Room air' 'On supplemental oxygen'", example = "Room air")
    private String oxygenSaturationMode;
    @Schema(description = "The timestamp for when the vitals were collected", example = "2024-01-15 06:09:03")
    @ValidTimestamp
    private String vitalSignDate;
    private Boolean voided = false;
}
