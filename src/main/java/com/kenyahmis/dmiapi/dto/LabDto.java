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
    @Schema(description = "The unique identifier for the lab order request within the submitting EMR")
    @NotBlank
    private String orderId;
    @NotBlank
    private String testName;
    private String unit;
    private String upperLimit;
    private String lowerLimit;
    private String testResult;
    @ValidTimestamp
    private String labDate;
    private Boolean voided = false;

    public LabDto() {}
}
