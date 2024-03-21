package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidGender;
import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "subject")
public class SubjectDto {
    @Schema(description = "A unique identifier for the patient within the submitting EMR", example = "8599284")
    @NotBlank
    private String patientUniqueId;
    @Schema(description = "The MOH unique patient identifier", example = "MOH98898325")
    private String nupi;
    @Schema(description = "The subject's sex. Options include 'MALE','FEMALE', 'OTHER'", example = "FEMALE")
    @NotBlank
    @ValidGender
    private String sex;
    @Schema(description = "The Subject's physical address", example = "Mvita estate")
    private String address;
    @Schema(description = "The subject's date of birth", example = "2024-01-17 00:00:00")
    @ValidTimestamp
    private String dateOfBirth;
    @Schema(example = "Mombasa")
    private String county;
    @Schema(example = "Mvita")
    private String subCounty;
}

