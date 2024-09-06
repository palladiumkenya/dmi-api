package com.kenyahmis.dmiapi.dto;

import com.kenyahmis.dmiapi.validator.ValidFlaggedCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "flaggedConditions")
public class FlaggedConditionDto {
    @NotBlank
    @Schema(example = "3455", description = "The unique identifier for the flagged condition")
    private String conditionId;
    @Schema(description = "Condition that was automatically flagged by the EMR. Options include: Acceptable syndromes are: " +
            "ACUTE JAUNDICE SYNDROME, ACUTE MENINGITIS AND ENCEPHALITIS  SYNDROME, ACUTE FLACCID PARALYSIS, " +
            "SEVERE ACUTE RESPIRATORY INFECTION, ACUTE HAEMORRHAGIC FEVER, ACUTE WATERY DIARRHOEAL, NEUROLOGICAL  SYNDROME, " +
            "ACUTE FEBRILE RASH INFECTIONS, ACUTE FEBRILE ILLNESS, ACUTE HAEMORRHAGIC FEVER, MPOX, INFLUENZA LIKE ILLNESS, " +
            "SEVERE ACUTE RESPIRATORY INFECTION", example = "NEUROLOGICAL  SYNDROME")
    @NotBlank
    @ValidFlaggedCondition
    private String conditionName;
    @Schema(example = "false")
    private Boolean voided = false;
}
