package com.kenyahmis.dmiapi.controller;

import com.kenyahmis.dmiapi.exception.ResourceNotFoundException;
import com.kenyahmis.dmiapi.model.APIResponse;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.service.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BatchController {

    private final BatchService batchService;

    public BatchController(KafkaTemplate<String, Object> kafkaTemplate, BatchService batchService) {
        this.batchService = batchService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/batch/{batchId}")
    private APIResponse<BatchOperation> getBatchOperation(@PathVariable UUID batchId) throws ResourceNotFoundException {
        BatchOperation batchOperation = batchService.getBatchOperation(batchId);
        return new APIResponse<>(batchOperation, "Success");
    }
}
