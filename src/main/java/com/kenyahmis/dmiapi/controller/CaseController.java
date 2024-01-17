package com.kenyahmis.dmiapi.controller;

import com.kenyahmis.dmiapi.dto.CaseMessageDto;
import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.dto.ValidList;
import com.kenyahmis.dmiapi.model.BatchAPIResponse;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import com.kenyahmis.dmiapi.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CaseController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BatchService batchService;
    private final BatchOperationsRepository batchOperationsRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CaseController.class);

    public CaseController(KafkaTemplate<String, Object> kafkaTemplate, BatchOperationsRepository batchOperationsRepository,
                          BatchService batchService) {
        this.kafkaTemplate = kafkaTemplate;
        this.batchOperationsRepository = batchOperationsRepository;
        this.batchService = batchService;
    }

//    @PostMapping(value = "/case")
//    private ResponseEntity<?> addCase(@RequestBody @Valid IllnessRequest request, @AuthenticationPrincipal Jwt jwt) {
//        // Create Batch Entry
//        BatchOperation batch = batchService.createBatchOperation(request.getBatchId(), request.getTotalCases(), request.getMflCode());
////        LOGGER.info("Working with request from {}", jwt.getClaimAsString("emr"));
//        request.getCases().forEach(r -> kafkaTemplate.send("visitTopic", new CaseMessageDto(batch.getId(), r,  jwt.getClaimAsString("emr"))));
//        return new ResponseEntity<>(new BatchAPIResponse("Success", batch.getId().toString()), HttpStatus.OK);
//    }

    @Operation(summary = "Submit a case report. The API creates a case report in the staging area and updates it if an exising one is found")
    @PostMapping(value = "/case/batch")
    private ResponseEntity<?> addBatchCases(@RequestBody @Valid ValidList<CaseDto> request,
                                            @AuthenticationPrincipal Jwt jwt) {
        // Create Batch Entry
        BatchOperation batch = new BatchOperation(request.size(), "INCOMPLETE",
                request.get(0).getHospitalIdNumber(), LocalDateTime.now());
        LOGGER.info("Working with request from {}", jwt.getClaimAsString("emr"));
        batchOperationsRepository.save(batch);
        request.forEach(r -> kafkaTemplate.send("visitTopic", new CaseMessageDto(batch.getId(), r,  jwt.getClaimAsString("emr"))));
        return new ResponseEntity<>(new BatchAPIResponse("Success", batch.getId().toString()), HttpStatus.OK);
    }
}
