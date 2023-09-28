package com.kenyahmis.dmiapi.controller;

import com.kenyahmis.dmiapi.dto.BatchOperationDto;
import com.kenyahmis.dmiapi.dto.CaseMessageDto;
import com.kenyahmis.dmiapi.dto.IllnessCaseDto;
import com.kenyahmis.dmiapi.model.APIResponse;
import com.kenyahmis.dmiapi.model.BatchAPIResponse;
import com.kenyahmis.dmiapi.model.BatchOperations;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import com.kenyahmis.dmiapi.repository.RespiratoryIllnessCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final KafkaTemplate<String, Object>  kafkaTemplate;
    private final BatchOperationsRepository batchOperationsRepository;
    private final RespiratoryIllnessCaseRepository illnessCaseRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    public ApiController(KafkaTemplate<String, Object> kafkaTemplate, BatchOperationsRepository batchOperationsRepository,
                         RespiratoryIllnessCaseRepository illnessCaseRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.batchOperationsRepository = batchOperationsRepository;
        this.illnessCaseRepository = illnessCaseRepository;
    }

    @PostMapping(value = "/case")
    private ResponseEntity<?> addCase(@RequestBody IllnessCaseDto request){
        kafkaTemplate.send("visitTopic", request);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/batch/{batchId}")
    private ResponseEntity<APIResponse<BatchOperationDto>> getBatchOperation(@PathVariable UUID batchId){
        List<Object[]> results = batchOperationsRepository.findByRefId(batchId);
        if (results != null && results.size() > 0){
            Object[] batchOperation = results.get(0);
            return new ResponseEntity<>(new APIResponse<>(BatchOperationDto.mapNativeResult(batchOperation),"Success"), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @PostMapping(value = "/case/batch")
    private ResponseEntity<?> addBatchCases(@RequestBody List<IllnessCaseDto> request, @AuthenticationPrincipal Jwt jwt){
        // Create Batch Entry
        BatchOperations batch = new BatchOperations(request.size(), "INCOMPLETE", LocalDateTime.now());
        LOGGER.info("Working with request from {}", jwt.getClaimAsString("emr"));
        batchOperationsRepository.save(batch);
        request.forEach(r -> kafkaTemplate.send("visitTopic", new CaseMessageDto(batch.getId(), r)));

        // Update Batch entry
        Integer completedCount = illnessCaseRepository.countByBatchId(batch.getId());
        batch.setCompleteTime(LocalDateTime.now());
        batch.setStatus("COMPLETE");
        batch.setProcessedCount(completedCount);
        batchOperationsRepository.save(batch);
        return new ResponseEntity<>(new BatchAPIResponse("Success", batch.getId().toString()), HttpStatus.OK);
    }
}
