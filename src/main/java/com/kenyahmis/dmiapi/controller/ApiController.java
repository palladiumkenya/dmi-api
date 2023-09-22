package com.kenyahmis.dmiapi.controller;

import com.kenyahmis.dmiapi.model.RespiratoryIllnessCaseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final KafkaTemplate<String, Object>  kafkaTemplate;

    public ApiController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(value = "/case")
    private ResponseEntity<?> addCase(@RequestBody RespiratoryIllnessCaseDto request){
        kafkaTemplate.send("visitTopic", request);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/case/batch")
    private ResponseEntity<?> addBatchCases(@RequestBody List<RespiratoryIllnessCaseDto> request){
        request.forEach(r -> kafkaTemplate.send("visitTopic", r));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
