package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "artLinkage")
public class ARTLinkageDto {
    @Schema(description = "The unique identifier for the linkage entry", example = "765789")
    @NotBlank
    private String linkageId;
    @Schema(description = "The subject ART start date", example = "2024-01-17 00:00:00")
    @ValidTimestamp
    private String artStartDate;
}
