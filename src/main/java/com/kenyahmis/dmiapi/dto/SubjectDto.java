package com.kenyahmis.dmiapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubjectDto {
    @Schema(description = "A unique identifier for the patient within the submitting EMR")
    @NotBlank
    private String patientUniqueId;
    @Schema(description = "The MOH unique patient identifier")
    private String nupi;
    @Schema(description = "The subject's sex. Options include 'MALE','FEMALE', 'OTHER'")
    @NotBlank
    private String sex;
    @Schema(description = "The Subject's physical address")
    private String address;
    @Schema(description = "The subject's date of birth", example = "2024-01-17 00:00:00")
    @ValidTimestamp
    private String dateOfBirth;
    @NotBlank
    private String county;
    @NotBlank
    private String subCounty;
}

