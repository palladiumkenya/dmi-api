package com.kenyahmis.dmiapi.controller;

import com.kenyahmis.dmiapi.exception.ResourceNotFoundException;
import com.kenyahmis.dmiapi.model.APIResponse;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.service.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BatchController {

    private final BatchService batchService;

    public BatchController(KafkaTemplate<String, Object> kafkaTemplate, BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping(value = "/batch/{batchId}")
    private ResponseEntity<APIResponse<BatchOperation>> getBatchOperation(@PathVariable UUID batchId) throws ResourceNotFoundException {
        BatchOperation batchOperation = batchService.getBatchOperation(batchId);
        return new ResponseEntity<>(new APIResponse<>(batchOperation, "Success"), HttpStatus.OK);
    }
}
