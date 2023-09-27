package com.kenyahmis.dmiapi.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class BatchOperationDto {
    private UUID refId;
    private Integer inputCount;
    private String status;
    private Integer processedCount;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;

    public BatchOperationDto() {
    }

    public BatchOperationDto(UUID refId, Integer inputCount, String status, Integer processedCount, LocalDateTime startTime, LocalDateTime completeTime) {
        this.refId = refId;
        this.inputCount = inputCount;
        this.status = status;
        this.processedCount = processedCount;
        this.startTime = startTime;
        this.completeTime = completeTime;
    }

    public UUID getRefId() {
        return refId;
    }

    public void setRefId(UUID refId) {
        this.refId = refId;
    }

    public Integer getInputCount() {
        return inputCount;
    }

    public void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(Integer processedCount) {
        this.processedCount = processedCount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }
    public static BatchOperationDto mapNativeResult(Object[] result) {
        return  new BatchOperationDto(
                (UUID) result[0],
                (Integer) result[1],
                (String) result[2],
                ((Long) result[3]).intValue(),
                result[4] != null ? ((Timestamp) result[4]).toLocalDateTime() : null,
                result[5] != null ? ((Timestamp) result[5]).toLocalDateTime() : null
        );
    }
}
