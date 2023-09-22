package com.kenyahmis.dmiapi.consumer;

import com.kenyahmis.dmiapi.model.RespiratoryIllnessCase;
import com.kenyahmis.dmiapi.model.RespiratoryIllnessCaseDto;
import com.kenyahmis.dmiapi.repository.RespiratoryIllnessCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitExtractConsumer {

    private final RespiratoryIllnessCaseRepository caseRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(VisitExtractConsumer.class);

    public VisitExtractConsumer(RespiratoryIllnessCaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @KafkaListener(id = "visitListener", topics = "visitTopic", containerFactory = "kafkaListenerContainerFactory")
    public void listenToMessage(List<RespiratoryIllnessCaseDto> messages) {
        messages.forEach(m -> {
            Optional<RespiratoryIllnessCase>  optionalRespiratoryIllnessCase = caseRepository.findByVisitUniqueIdAndMflCode(m.getVisitUniqueId(), m.getHospitalIdNumber());
            RespiratoryIllnessCase respiratoryIllnessCase;
            if (optionalRespiratoryIllnessCase.isPresent()) {
                // updated case
                respiratoryIllnessCase = optionalRespiratoryIllnessCase.get();
                respiratoryIllnessCase.setPatientUniqueId(m.getPatientUniqueId());
                respiratoryIllnessCase.setVisitUniqueId(m.getVisitUniqueId());
                respiratoryIllnessCase.setMflCode(m.getHospitalIdNumber());
                respiratoryIllnessCase.setInterviewDate(m.getInterviewDate());
                respiratoryIllnessCase.setVerbalConsentingDone(m.getVerbalConsentDone());
                respiratoryIllnessCase.setDateOfBirth(m.getDateOfBirth());
                respiratoryIllnessCase.setInfantAge(m.getInfantAge());
                respiratoryIllnessCase.setAge(m.getAge());
                respiratoryIllnessCase.setAddress(m.getAddress());
                respiratoryIllnessCase.setIllnessOnsetDate(m.getIllnessOnsetDate());
                respiratoryIllnessCase.setAdmissionDate(m.getAdmissionDate());
                respiratoryIllnessCase.setOutpatientDate(m.getOutpatientDate());
                respiratoryIllnessCase.setTemperature(m.getTemperature());
                respiratoryIllnessCase.setInfantComplaints(m.getInfantComplaints());
                respiratoryIllnessCase.setComplaints(m.getComplaints());
                respiratoryIllnessCase.setDurationOfComplaints(m.getDurationOfComplaints());
                respiratoryIllnessCase.setCounty(m.getCounty());
                respiratoryIllnessCase.setSubCounty(m.getSubCounty());
                respiratoryIllnessCase.setCreatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setUpdatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setLoadDate(LocalDateTime.now());
            } else {
                // created new case
                respiratoryIllnessCase = new RespiratoryIllnessCase();
                respiratoryIllnessCase.setPatientUniqueId(m.getPatientUniqueId());
                respiratoryIllnessCase.setVisitUniqueId(m.getVisitUniqueId());
                respiratoryIllnessCase.setMflCode(m.getHospitalIdNumber());
                respiratoryIllnessCase.setInterviewDate(m.getInterviewDate());
                respiratoryIllnessCase.setVerbalConsentingDone(m.getVerbalConsentDone());
                respiratoryIllnessCase.setDateOfBirth(m.getDateOfBirth());
                respiratoryIllnessCase.setInfantAge(m.getInfantAge());
                respiratoryIllnessCase.setAge(m.getAge());
                respiratoryIllnessCase.setAddress(m.getAddress());
                respiratoryIllnessCase.setIllnessOnsetDate(m.getIllnessOnsetDate());
                respiratoryIllnessCase.setAdmissionDate(m.getAdmissionDate());
                respiratoryIllnessCase.setOutpatientDate(m.getOutpatientDate());
                respiratoryIllnessCase.setTemperature(m.getTemperature());
                respiratoryIllnessCase.setInfantComplaints(m.getInfantComplaints());
                respiratoryIllnessCase.setComplaints(m.getComplaints());
                respiratoryIllnessCase.setDurationOfComplaints(m.getDurationOfComplaints());
                respiratoryIllnessCase.setCounty(m.getCounty());
                respiratoryIllnessCase.setSubCounty(m.getSubCounty());
                respiratoryIllnessCase.setCreatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setUpdatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setLoadDate(LocalDateTime.now());
            }
            LOGGER.info("Consumed message is: {}", m.toString());
            caseRepository.save(respiratoryIllnessCase);
        });
    }

}
