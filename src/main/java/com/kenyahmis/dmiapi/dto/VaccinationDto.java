package com.kenyahmis.dmiapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VaccinationDto {
    @NotBlank
    private String vaccinationId;
    @NotBlank
    private String vaccination;
    private Integer doses;
    private Boolean verified;
    private Boolean voided;
}
