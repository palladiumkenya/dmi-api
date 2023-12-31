//package com.kenyahmis.dmiapi.controller;
//
//import com.kenyahmis.dmiapi.dto.BatchOperationDto;
//import com.kenyahmis.dmiapi.dto.CaseMessageDto;
//import com.kenyahmis.dmiapi.dto.IllnessCaseDto;
//import com.kenyahmis.dmiapi.dto.ValidList;
//import com.kenyahmis.dmiapi.model.APIResponse;
//import com.kenyahmis.dmiapi.model.BatchAPIResponse;
//import com.kenyahmis.dmiapi.model.BatchOperations;
//import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
//import com.kenyahmis.dmiapi.repository.RespiratoryIllnessCaseRepository;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api")
//public class ApiController {
//
//    private final KafkaTemplate<String, Object>  kafkaTemplate;
//    private final BatchOperationsRepository batchOperationsRepository;
//    private final RespiratoryIllnessCaseRepository illnessCaseRepository;
//    private final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
//
//    public ApiController(KafkaTemplate<String, Object> kafkaTemplate, BatchOperationsRepository batchOperationsRepository,
//                         RespiratoryIllnessCaseRepository illnessCaseRepository) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.batchOperationsRepository = batchOperationsRepository;
//        this.illnessCaseRepository = illnessCaseRepository;
//    }
//
//
//
//
//}
