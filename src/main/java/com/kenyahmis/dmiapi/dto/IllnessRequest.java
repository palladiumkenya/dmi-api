package com.kenyahmis.dmiapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class IllnessRequest {

    @NotEmpty
    private String batchId;
    private Integer totalCases;
    private String mflCode;
    @Valid
    private List<IllnessCaseDto> cases;

    public IllnessRequest(String batchId, Integer totalCases, @Valid List<IllnessCaseDto> cases) {
        this.batchId = batchId;
        this.totalCases = totalCases;
        this.cases = cases;
    }
}
