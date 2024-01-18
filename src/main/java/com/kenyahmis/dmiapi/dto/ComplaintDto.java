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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ComplaintDto {
    @Schema(description = "The unique identifier for the complaint within the submitting EMR")
    @NotBlank
    private String complaintId;
    @Schema(description = "The actual complaint reported by the subject", example = "Cough")
    @NotBlank
    private String complaint;
    private Boolean voided = false;
    @Schema(description = "The timestamp for when the complaint started", example = "2021-01-15 00:00:00")
    @ValidTimestamp
    private String onsetDate;
    @Schema(description = "The duration of reported complaint in days", example = "5")
    private Integer duration;

    public ComplaintDto() {
    }
}
