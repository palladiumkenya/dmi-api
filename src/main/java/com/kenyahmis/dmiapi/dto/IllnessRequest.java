package com.kenyahmis.dmiapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class IllnessRequest {
    @NotBlank
    private String batchId;
    @NotNull
    private Integer totalCases;
    @NotBlank
    private String mflCode;
    @Valid
    private List<CaseDto> cases;

    public IllnessRequest(String batchId, Integer totalCases, @Valid List<CaseDto> cases) {
        this.batchId = batchId;
        this.totalCases = totalCases;
        this.cases = cases;
    }
}
