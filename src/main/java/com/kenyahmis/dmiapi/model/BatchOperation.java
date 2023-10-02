package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "batch_operations")
public class BatchOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String externalReferenceId;
    private String mflCode;
    private Integer inputCount;
    private String status;
    private Integer processedCount;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;

    public BatchOperation(Integer inputCount, String status, String mflCode, LocalDateTime startTime) {
        this.inputCount = inputCount;
        this.mflCode = mflCode;
        this.status = status;
        this.startTime = startTime;
    }

    public BatchOperation(String externalReferenceId, Integer inputCount, String mflCode, String status, LocalDateTime startTime) {
        this.externalReferenceId = externalReferenceId;
        this.inputCount = inputCount;
        this.mflCode = mflCode;
        this.status = status;
        this.startTime = startTime;
    }
}
