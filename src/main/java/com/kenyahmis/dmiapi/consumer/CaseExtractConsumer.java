package com.kenyahmis.dmiapi.consumer;

import com.kenyahmis.dmiapi.dto.*;
import com.kenyahmis.dmiapi.mapper.CaseMapper;
import com.kenyahmis.dmiapi.model.*;
import com.kenyahmis.dmiapi.repository.*;
import com.kenyahmis.dmiapi.service.BatchService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CaseExtractConsumer {

    private final CaseRepository caseRepository;
    private final EmrRepository emrRepository;
    private final BatchService batchService;
    private final CaseMapper caseMapper;
    private final SubjectRepository subjectRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CaseExtractConsumer.class);

    public CaseExtractConsumer(CaseRepository caseRepository, BatchService batchService, EmrRepository emrRepository,
                               CaseMapper caseMapper, SubjectRepository subjectRepository ) {
        this.caseRepository = caseRepository;
        this.batchService = batchService;
        this.emrRepository = emrRepository;
        this.subjectRepository = subjectRepository;
        this.caseMapper = caseMapper;
    }

    @Transactional
    @KafkaListener(id = "visitListener", topics = "visitTopic", containerFactory = "kafkaListenerContainerFactory")
    public void listenToMessage(List<CaseMessageDto> messages) {
        Set<UUID> batchIdList = new HashSet<>();
        messages.forEach(caseMessageDto -> {
            CaseDto caseDto = caseMessageDto.getCaseDto();
            Optional<IllnessCase>  optionalIllnessCase = caseRepository.findByVisitUniqueIdAndMflCode(caseDto.getCaseUniqueId(), caseDto.getHospitalIdNumber());
            IllnessCase aCase;
            UUID emrId = null;
            Optional<Emr> emr = emrRepository.findByEmrNameAndVoided(caseMessageDto.getEmr(), false);
            if (emr.isPresent()) {
                emrId = emr.get().getId();
            }

            if (optionalIllnessCase.isPresent()) {
                LOGGER.info("Updating an existing case");
                // updated case
                aCase = caseMapper.caseDtoToIllnessCase(caseDto, optionalIllnessCase.get());
                IllnessCase savedCase = optionalIllnessCase.get();
                // update existing elements
                aCase.setId(savedCase.getId());
                if (aCase.getSubject().getPatientUniqueId().equals(savedCase.getSubject().getPatientUniqueId())) {
                    aCase.getSubject().setId(savedCase.getSubject().getId());
                }
                aCase.getComplaints().forEach(complaint -> {
                    UUID uuid = savedCase.getComplaints().stream()
                            .filter(c -> c.getComplaintId().equals(complaint.getComplaintId()))
                            .map(Complaint::getId)
                            .findFirst().orElse(null);
                    complaint.setId(uuid);
                });
                aCase.getDiagnosis().forEach(diagnosis -> {
                    UUID uuid = savedCase.getDiagnosis().stream()
                            .filter(d ->d.getDiagnosisId().equals(diagnosis.getDiagnosisId()))
                            .map(Diagnosis::getId)
                            .findFirst().orElse(null);
                    diagnosis.setId(uuid);
                });
                aCase.getVaccinations().forEach(vaccination -> {
                    UUID uuid = savedCase.getVaccinations()
                            .stream()
                            .filter(v -> v.getVaccinationId().equals(vaccination.getVaccinationId()))
                            .map(Vaccination::getId)
                            .findFirst().orElse(null);
                    vaccination.setId(uuid);
                });
                aCase.getVitalSigns().forEach(vitalSign -> {
                    UUID uuid = savedCase.getVitalSigns()
                            .stream()
                            .filter(v -> v.getVitalSignId().equals(vitalSign.getVitalSignId()))
                            .map(VitalSign::getId)
                            .findFirst().orElse(null);
                    vitalSign.setId(uuid);
                });
                aCase.getLabs().forEach(lab -> {
                    UUID uuid = savedCase.getLabs()
                            .stream()
                            .filter(v -> v.getOrderId().equals(lab.getOrderId()))
                            .map(Lab::getId)
                            .findFirst().orElse(null);
                    lab.setId(uuid);
                });
                aCase.getRiskFactors().forEach(riskFactor -> {
                    UUID uuid = savedCase.getRiskFactors()
                            .stream()
                            .filter(r -> r.getRiskFactorId().equals(riskFactor.getRiskFactorId()))
                            .map(RiskFactor::getId)
                            .findFirst().orElse(null);
                    riskFactor.setId(uuid);
                });
                aCase.getFlaggedConditions().forEach(flaggedCondition -> {
                    UUID uuid = savedCase.getFlaggedConditions()
                            .stream()
                            .filter(f -> f.getConditionId().equals(flaggedCondition.getConditionId()))
                            .map(FlaggedCondition::getId)
                            .findFirst().orElse(null);
                    flaggedCondition.setId(uuid);
                });
            } else {
                // new case
                LOGGER.info("Creating a new case");
                aCase = caseMapper.caseDtoToIllnessCase(caseDto, null);
                // resolve subject
                Page<SubjectSummary> subjectPage = subjectRepository
                        .findByPatientUniqueIdAndSiteCode(aCase.getSubject().getPatientUniqueId(), aCase.getMflCode(),
                                Pageable.ofSize(1));
//                   AtomicReference<UUID> subjectId = new AtomicReference<>();
                    subjectPage.getContent()
                            .stream()
                            .findFirst()
                            .ifPresent(subjectSummary -> {
                                LOGGER.info("Found existing subjects (PatientIdentifier:mflCode:id) {}:{}:{}",
                                        aCase.getSubject().getPatientUniqueId(), aCase.getMflCode(), subjectSummary.getSubjectId());
                                Subject foundSubject = aCase.getSubject();
                                foundSubject.setId(subjectSummary.getSubjectId());
                                aCase.setSubject(null);
                                // insert case record
                                caseRepository.save(aCase);
                                // update case record
                                aCase.setSubject(foundSubject);
                            } );
//                caseRepository.save(aCase);
            }
            aCase.setBatchId(caseMessageDto.getBatchId());
            aCase.setEmrId(emrId);
            caseRepository.save(aCase);
            LOGGER.info("caseId: {}", aCase.getId());
            batchIdList.add(caseMessageDto.getBatchId());
        });
        batchIdList.forEach(batchService::updateBatchOperation);
    }
}
