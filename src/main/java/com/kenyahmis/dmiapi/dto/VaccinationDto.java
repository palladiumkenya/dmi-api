package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class VaccinationDto {
    @NotBlank
    @Schema(description = "The unique identifier for the vaccination within the submitting EMR", example = "98765")
    private String vaccinationId;
    @Schema(description = "Name of the vaccination administered to the subject", example = "Covid")
    @NotBlank
    private String vaccination;
    @Schema(example = "2")
    private Integer doses;
    @Schema(description = "Was the vaccination verified using a vaccination card or hospital records", example = "true")
    private Boolean verified;
    @Schema(example = "false")
    private Boolean voided = false;
}
