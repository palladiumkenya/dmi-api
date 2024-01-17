package com.kenyahmis.dmiapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CaseMessageDto {
    private UUID batchId;
    private String emr;
    private CaseDto caseDto;

    public CaseMessageDto() {}

    public CaseMessageDto(UUID batchId, CaseDto caseDto, String emr) {
        this.batchId = batchId;
        this.caseDto = caseDto;
        this.emr = emr;
    }

}
