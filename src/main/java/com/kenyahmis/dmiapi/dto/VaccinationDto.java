package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VaccinationDto {
    @NotBlank
    @Schema(description = "The unique identifier for the vaccination within the submitting EMR")
    private String vaccinationId;
    @Schema(description = "Name of the vaccination administered to the subject", example = "Covid")
    @NotBlank
    private String vaccination;
    private Integer doses;
    @Schema(description = "Was the vaccination verified using a vaccination card or hospital records")
    private Boolean verified;
    private Boolean voided = false;
}
