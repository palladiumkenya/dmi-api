package com.kenyahmis.dmiapi.service;

import com.kenyahmis.dmiapi.exception.ResourceNotFoundException;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import com.kenyahmis.dmiapi.repository.IllnessCaseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BatchService {

    private final BatchOperationsRepository batchOperationsRepository;
    private final IllnessCaseRepository illnessCaseRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(BatchService.class);

    public BatchService(BatchOperationsRepository batchOperationsRepository, IllnessCaseRepository illnessCaseRepository) {
        this.batchOperationsRepository = batchOperationsRepository;
        this.illnessCaseRepository = illnessCaseRepository;
    }

    public BatchOperation getBatchOperation(UUID id) throws ResourceNotFoundException {
        Optional <BatchOperation> optionalBatchOperation = batchOperationsRepository.findById(id);
        if (optionalBatchOperation.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Batch operation %s not found", id));
        }
        return optionalBatchOperation.get();
    }

    public BatchOperation createBatchOperation(String externalRefId, Integer totalCases, String siteCode) {
        Optional<BatchOperation> optionalBatchOperation = batchOperationsRepository.findByExternalReferenceIdAndMflCode(externalRefId, siteCode);
        BatchOperation batchOperation;
        if (optionalBatchOperation.isEmpty()) {
            BatchOperation batch = new BatchOperation(externalRefId, totalCases, siteCode, "INCOMPLETE", LocalDateTime.now());
            batchOperationsRepository.save(batch);
            batchOperation = batch;
        } else {
            batchOperation = optionalBatchOperation.get();
        }
        return batchOperation;
    }

    public void updateBatchOperation(UUID batchId) {
        Optional<BatchOperation> optionalBatchOperation = batchOperationsRepository.findById(batchId);
        if (optionalBatchOperation.isEmpty()) {
            LOGGER.error("Failed to update Batch operation:  {} not found", batchId);
        } else {
            BatchOperation batchOperation = optionalBatchOperation.get();
            Integer caseCount = illnessCaseRepository.countByBatchId(batchOperation.getId());
            batchOperation.setProcessedCount(caseCount);
            if (caseCount.equals(batchOperation.getInputCount()) || caseCount > batchOperation.getInputCount()) {
                batchOperation.setStatus("COMPLETE");
                batchOperation.setCompleteTime(LocalDateTime.now());
            }
            batchOperationsRepository.save(batchOperation);
        }
    }
}
