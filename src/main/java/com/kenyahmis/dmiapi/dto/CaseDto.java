package com.kenyahmis.dmiapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenyahmis.dmiapi.validator.ValidCaseStatus;
import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(name = "case", hidden = true)
public class CaseDto {

    @Schema(description = "unique id for the submitted case. The value should be generated by the submitting EMR" +
            " and should be unique for each case", example = "98754433566")
    @NotBlank
    private String caseUniqueId;
    @Schema(description = "The facility MFLCode", example = "18529")
    @NotBlank
    private String hospitalIdNumber;
    @Schema(description = "The status of the submitted case report. Options include preliminary | final | amended | entered-in-error", example = "final")
    @NotBlank
    @ValidCaseStatus
    private String status;
    @Schema(description = "The final clinical outcome of the reported case. Options include: 'Discharge from hospital'," +
            " 'Refused hospital treatment', 'Absconded', 'Death', 'Referred to another facility'", example = "Absconded")
    private String finalOutcome;
    @Schema(description = "Timestamp for the recorded final outcome", example = "2024-01-17 06:00:00")
    @ValidTimestamp
    private String finalOutcomeDate;
    @Schema(description = "Timestamp for the case consultation", example = "2024-01-17 06:00:00")
    @ValidTimestamp
    private String interviewDate;
    @Schema(description = "Timestamp for when the subject was admitted", example = "2024-01-17 06:00:00")
    @ValidTimestamp
    private String admissionDate;
    @Schema(example = "2024-01-17 06:00:00")
    @ValidTimestamp
    private String outpatientDate;
    @Schema(description = "A timestamp for when the case report was generated. This should be system generated", example = "2024-01-17 06:50:17")
    @ValidTimestamp
    private String createdAt;
    @Schema(description = "A timestamp for when the case report was last updated. This should be system generated", example = "2024-01-17 06:50:17")
    @ValidTimestamp
    private String updatedAt;
    @NotNull
    @Valid
    private SubjectDto subject;
    @NotNull(message = "Flagged conditions cannot be null")
    @NotEmpty(message = "Flagged conditions cannot be empty")
    @Valid
    @JsonProperty("flaggedConditions")
    private Set<FlaggedConditionDto> flaggedConditionDtoList = new HashSet<>();
    @Valid
    @JsonProperty("vitalSigns")
    private Set<VitalSignsDto> vitalSigns = new HashSet<>();
    @Valid
    @JsonProperty("riskFactors")
    private Set<RiskFactorDto> riskFactorDtoList = new HashSet<>();
    @Valid
    @JsonProperty("vaccinations")
    private Set<VaccinationDto> vaccinationDtoList = new HashSet<>();
    @Valid
    private Set<DiagnosisDto> diagnosis = new HashSet<>();
    @JsonProperty("complaints")
    @Valid
    private Set<ComplaintDto> complaintDtoList = new HashSet<>();
    @JsonProperty("lab")
    @Valid
    private Set<LabDto> labDtoList = new HashSet<>();
}
