package com.kenyahmis.dmiapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenyahmis.dmiapi.dto.CaseMessageDto;
import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.dto.ValidList;
import com.kenyahmis.dmiapi.model.APIErrorResponse;
import com.kenyahmis.dmiapi.model.BatchAPIResponse;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
import java.util.function.Consumer;

@RestController
@RequestMapping("/api")
public class CaseController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BatchOperationsRepository batchOperationsRepository;
    private final Logger LOG = LoggerFactory.getLogger(CaseController.class);

    public CaseController(KafkaTemplate<String, Object> kafkaTemplate, BatchOperationsRepository batchOperationsRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.batchOperationsRepository = batchOperationsRepository;
    }

    @Operation(summary = "Submit a case report. The API creates a case report in the staging area and updates it if an exising one is found")
    @PostMapping(value = "/case/batch")
    private ResponseEntity<?> addBatchCases(@RequestBody @Valid ValidList<CaseDto> request,
                                            @AuthenticationPrincipal Jwt jwt) {
        // Create Batch Entry
        if (request.isEmpty()) {
            LOG.warn("Empty request body found");
            return new ResponseEntity<>(new APIErrorResponse<>(null, "Empty Request body"), HttpStatus.BAD_REQUEST);
        }
        BatchOperation batch = new BatchOperation(request.size(), "INCOMPLETE",
                request.get(0).getHospitalIdNumber(), LocalDateTime.now());
        LOG.info("Working with request from {}", jwt.getClaimAsString("emr"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            String reqAsString = mapper.writeValueAsString(request);
            LOG.debug("request body: {}", reqAsString);
        } catch (JsonProcessingException je) {
            LOG.error("Failed to parse request", je);
        }

        batchOperationsRepository.save(batch);
        request.forEach((Consumer<? super CaseDto>) r -> kafkaTemplate.send("visitTopic", new CaseMessageDto(batch.getId(), r,  jwt.getClaimAsString("emr"))));
        return new ResponseEntity<>(new BatchAPIResponse("Success", batch.getId().toString()), HttpStatus.OK);
    }
}
