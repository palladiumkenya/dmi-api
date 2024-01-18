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
public class LabDto {
    @Schema(description = "The unique identifier for the lab order request within the submitting EMR", example = "88997")
    @NotBlank
    private String orderId;
    @Schema(example = "Blood panel")
    @NotBlank
    private String testName;
    @Schema(example = "mmol/L")
    private String unit;
    @Schema(example = "5")
    private String upperLimit;
    @Schema(example = "1")
    private String lowerLimit;
    private String testResult;
    @Schema(example = "2024-01-17 06:50:17")
    @ValidTimestamp
    private String labDate;
    private Boolean voided = false;

    public LabDto() {}
}
