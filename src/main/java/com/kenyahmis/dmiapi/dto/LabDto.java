package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    @Schema(example = "false")
    private Boolean voided = false;

    public LabDto() {}
}
