package com.kenyahmis.dmiapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class IllnessRequest {
    @NotEmpty
    private String batchId;
    @NotNull
    private Integer totalCases;
    @NotEmpty
    private String mflCode;
    @Valid
    private List<IllnessCaseDto> cases;

    public IllnessRequest(String batchId, Integer totalCases, @Valid List<IllnessCaseDto> cases) {
        this.batchId = batchId;
        this.totalCases = totalCases;
        this.cases = cases;
    }
}
