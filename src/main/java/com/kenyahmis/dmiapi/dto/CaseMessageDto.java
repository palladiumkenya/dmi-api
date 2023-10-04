package com.kenyahmis.dmiapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CaseMessageDto {
    private UUID batchId;
    private String emr;
    private  IllnessCaseDto illnessCaseDto;

    public CaseMessageDto() {}

    public CaseMessageDto(UUID batchId, IllnessCaseDto illnessCaseDto, String emr) {
        this.batchId = batchId;
        this.illnessCaseDto = illnessCaseDto;
        this.emr = emr;
    }

}
