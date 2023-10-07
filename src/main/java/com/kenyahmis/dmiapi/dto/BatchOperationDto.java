package com.kenyahmis.dmiapi.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
