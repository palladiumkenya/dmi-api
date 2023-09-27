package com.kenyahmis.dmiapi.dto;

import java.util.UUID;

public class CaseMessageDto {
    private UUID batchId;
    private  IllnessCaseDto illnessCaseDto;

    public CaseMessageDto() {
    }

    public CaseMessageDto(UUID batchId, IllnessCaseDto illnessCaseDto) {
        this.batchId = batchId;
        this.illnessCaseDto = illnessCaseDto;
    }

    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    public IllnessCaseDto getIllnessCaseDto() {
        return illnessCaseDto;
    }

    public void setIllnessCaseDto(IllnessCaseDto illnessCaseDto) {
        this.illnessCaseDto = illnessCaseDto;
    }
}
