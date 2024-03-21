package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidFlaggedCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "flaggedConditions")
public class FlaggedConditionDto {
    @NotBlank
    private String conditionId;
    @Schema(description = "Condition that was automatically flagged by the EMR. Options include: DYSENTERY, CHOLERA," +
            " ILI, SARI, RIFT VALLEY FEVER, MALARIA, CHIKUNGUNYA, POLIOMYELITIS, VIRAL HAEMORRHAGIC FEVER, MEASLES", example = "DYSENTERY")
    @NotBlank
    @ValidFlaggedCondition
    private String conditionName;
    @Schema(example = "false")
    private Boolean voided = false;
}
