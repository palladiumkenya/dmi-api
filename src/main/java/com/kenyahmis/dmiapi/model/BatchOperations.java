package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "batch_operations")
public class BatchOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer inputCount;
    private String status;
    private Integer processedCount;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;

    public BatchOperations() {
    }

    public BatchOperations(Integer inputCount, String status, LocalDateTime startTime) {
        this.inputCount = inputCount;
        this.status = status;
        this.startTime = startTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
