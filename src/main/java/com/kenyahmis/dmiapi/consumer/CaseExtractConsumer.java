package com.kenyahmis.dmiapi.consumer;

import com.kenyahmis.dmiapi.dto.*;
import com.kenyahmis.dmiapi.model.Complaint;
import com.kenyahmis.dmiapi.model.Diagnosis;
import com.kenyahmis.dmiapi.model.Lab;
import com.kenyahmis.dmiapi.model.RespiratoryIllnessCase;
import com.kenyahmis.dmiapi.repository.ComplaintRepository;
import com.kenyahmis.dmiapi.repository.DiagnosisRepository;
import com.kenyahmis.dmiapi.repository.LabRepository;
import com.kenyahmis.dmiapi.repository.IllnessCaseRepository;
import com.kenyahmis.dmiapi.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CaseExtractConsumer {

    private final IllnessCaseRepository caseRepository;
    private final LabRepository labRepository;
    private final ComplaintRepository complaintRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final BatchService batchService;
    private final Logger LOGGER = LoggerFactory.getLogger(CaseExtractConsumer.class);

    public CaseExtractConsumer(IllnessCaseRepository caseRepository, LabRepository labRepository, BatchService batchService,
                               ComplaintRepository complaintRepository, DiagnosisRepository diagnosisRepository) {
        this.caseRepository = caseRepository;
        this.labRepository = labRepository;
        this.batchService = batchService;
        this.complaintRepository = complaintRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    private List<Lab> mapLabDtoToLab(List<LabDto> labDtoList, RespiratoryIllnessCase illnessCase)  {
        List<Lab> labList = new ArrayList<>();
        labDtoList.forEach(labDto->{
            Lab lab = new Lab();
            lab.setLabDate(labDto.getLabDate());
            lab.setOrderId(labDto.getOrderId());
            lab.setTestName(labDto.getTestName());
            lab.setTestResult(labDto.getTestResult());
            lab.setVoided(labDto.getVoided());
            lab.setCaseId(illnessCase.getId());
            labList.add(lab);
        });
        return labList;
    }

    private List<Complaint> mapComplaintDtoToComplaint(List<ComplaintDto> complaintDtoList, RespiratoryIllnessCase illnessCase) {
        List<Complaint> complaintList = new ArrayList<>();
        complaintDtoList.forEach(complaintDto -> {
            Complaint complaint = new Complaint();
            complaint.setComplaintId(complaintDto.getComplaintId());
            complaint.setComplaint(complaintDto.getComplaint());
            complaint.setOnsetDate(complaintDto.getOnsetDate());
            complaint.setDuration(complaintDto.getDuration());
            complaint.setVoided(complaintDto.getVoided());
            complaint.setCaseId(illnessCase.getId());
            complaintList.add(complaint);
        });
        return complaintList;
    }

    private List<Diagnosis> mapDiagnosisDtoToDiagnosis(List<DiagnosisDto> diagnosisDtoList, RespiratoryIllnessCase illnessCase) {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisDtoList.forEach(diagnosisDto -> {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setDiagnosisId(diagnosisDto.getDiagnosisId());
            diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
            diagnosis.setDiagnosisDate(diagnosisDto.getDiagnosisDate());
            diagnosis.setVoided(diagnosisDto.getVoided());
            diagnosis.setCaseId(illnessCase.getId());
            diagnosisList.add(diagnosis);
        });
        return diagnosisList;
    }
    private void updateLabs(List<LabDto> labDtoList, RespiratoryIllnessCase illnessCase) {
        List<Lab> labList = new ArrayList<>();
        labDtoList.forEach(labDto -> {
            Optional<Lab> optionalLab = labRepository.findByCaseIdAndOrderId(illnessCase.getId(), labDto.getOrderId());
            if (optionalLab.isPresent()){
                Lab lab = optionalLab.get();
                lab.setTestResult(labDto.getTestResult());
                lab.setTestName(lab.getTestName());
                lab.setLabDate(labDto.getLabDate());
                lab.setVoided(labDto.getVoided());
                labList.add(lab);
            }else {
                Lab lab = new Lab();
                lab.setLabDate(labDto.getLabDate());
                lab.setOrderId(labDto.getOrderId());
                lab.setTestName(labDto.getTestName());
                lab.setTestResult(labDto.getTestResult());
                lab.setVoided(labDto.getVoided());
                lab.setCaseId(illnessCase.getId());
                labList.add(lab);
            }
        });
        labRepository.saveAll(labList);
    }

    private void updateComplaints(List<ComplaintDto> complaintDtoList, RespiratoryIllnessCase illnessCase) {
        List<Complaint> complaintList = new ArrayList<>();
        complaintDtoList.forEach(complaintDto -> {
            Optional<Complaint> optionalLab = complaintRepository.findByCaseIdAndComplaintId(illnessCase.getId(), complaintDto.getComplaintId());
            if (optionalLab.isPresent()){
                Complaint complaint = optionalLab.get();
                complaint.setComplaint(complaintDto.getComplaint());
                complaint.setOnsetDate(complaintDto.getOnsetDate());
                complaint.setVoided(complaintDto.getVoided());
                complaint.setDuration(complaintDto.getDuration());
                complaintList.add(complaint);
            }else {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(complaintDto.getComplaintId());
                complaint.setComplaint(complaintDto.getComplaint());
                complaint.setCaseId(illnessCase.getId());
                complaint.setOnsetDate(complaintDto.getOnsetDate());
                complaint.setDuration(complaintDto.getDuration());
                complaint.setVoided(complaintDto.getVoided());
                complaintList.add(complaint);
            }
        });
        complaintRepository.saveAll(complaintList);
    }

    private void updateDiagnosis(List<DiagnosisDto> diagnosisDtoList, RespiratoryIllnessCase illnessCase) {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisDtoList.forEach(diagnosisDto -> {
            Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findByCaseIdAndDiagnosisId(illnessCase.getId(), diagnosisDto.getDiagnosisId());
            if (optionalDiagnosis.isPresent()){
                Diagnosis diagnosis = optionalDiagnosis.get();
                diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
                diagnosis.setDiagnosisDate(diagnosisDto.getDiagnosisDate());
                diagnosis.setVoided(diagnosisDto.getVoided());
                diagnosisList.add(diagnosis);
            }else {
                Diagnosis diagnosis = new Diagnosis();
                diagnosis.setDiagnosisId(diagnosisDto.getDiagnosisId());
                diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
                diagnosis.setDiagnosisDate(diagnosisDto.getDiagnosisDate());
                diagnosis.setVoided(diagnosisDto.getVoided());
                diagnosis.setCaseId(illnessCase.getId());
                diagnosisList.add(diagnosis);
            }
        });
        diagnosisRepository.saveAll(diagnosisList);
    }

    @KafkaListener(id = "visitListener", topics = "visitTopic", containerFactory = "kafkaListenerContainerFactory")
    public void listenToMessage(List<CaseMessageDto> messages) {
        Set<UUID> batchIdList = new HashSet<>();
        messages.forEach(caseMessageDto -> {
            IllnessCaseDto m = caseMessageDto.getIllnessCaseDto();
            Optional<RespiratoryIllnessCase>  optionalRespiratoryIllnessCase = caseRepository.findByVisitUniqueIdAndMflCode(m.getCaseUniqueId(), m.getHospitalIdNumber());
            RespiratoryIllnessCase respiratoryIllnessCase;

            if (optionalRespiratoryIllnessCase.isPresent()) {
                // updated case
                respiratoryIllnessCase = optionalRespiratoryIllnessCase.get();
                respiratoryIllnessCase.setPatientUniqueId(m.getPatientUniqueId());
                respiratoryIllnessCase.setNupi(m.getNupi());
                respiratoryIllnessCase.setVisitUniqueId(m.getCaseUniqueId());
                respiratoryIllnessCase.setMflCode(m.getHospitalIdNumber());
                respiratoryIllnessCase.setDateOfBirth(m.getDateOfBirth());
                respiratoryIllnessCase.setAddress(m.getAddress());
                respiratoryIllnessCase.setAdmissionDate(m.getAdmissionDate());
                respiratoryIllnessCase.setOutpatientDate(m.getOutpatientDate());
                respiratoryIllnessCase.setTemperature(m.getTemperature());
                respiratoryIllnessCase.setCounty(m.getCounty());
                respiratoryIllnessCase.setSubCounty(m.getSubCounty());
                respiratoryIllnessCase.setCreatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setUpdatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setVoided(m.getVoided());
                respiratoryIllnessCase.setLoadDate(LocalDateTime.now());
                caseRepository.save(respiratoryIllnessCase);
                // update complaints
                updateComplaints(m.getComplaintDtoList(), respiratoryIllnessCase);

                // update labs
                updateLabs(m.getLabDtoList(), respiratoryIllnessCase);

                //update diagnosis
                updateDiagnosis(m.getDiagnosis(), respiratoryIllnessCase);
            } else {
                // created new case
                respiratoryIllnessCase = new RespiratoryIllnessCase();
                respiratoryIllnessCase.setBatchId(caseMessageDto.getBatchId());
                respiratoryIllnessCase.setPatientUniqueId(m.getPatientUniqueId());
                respiratoryIllnessCase.setNupi(m.getNupi());
                respiratoryIllnessCase.setVisitUniqueId(m.getCaseUniqueId());
                respiratoryIllnessCase.setMflCode(m.getHospitalIdNumber());
                respiratoryIllnessCase.setInterviewDate(m.getInterviewDate());
                respiratoryIllnessCase.setDateOfBirth(m.getDateOfBirth());
                respiratoryIllnessCase.setAddress(m.getAddress());
                respiratoryIllnessCase.setAdmissionDate(m.getAdmissionDate());
                respiratoryIllnessCase.setOutpatientDate(m.getOutpatientDate());
                respiratoryIllnessCase.setTemperature(m.getTemperature());
                respiratoryIllnessCase.setCounty(m.getCounty());
                respiratoryIllnessCase.setSubCounty(m.getSubCounty());
                respiratoryIllnessCase.setCreatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setUpdatedAt(m.getCreatedAt());
                respiratoryIllnessCase.setVoided(m.getVoided());
                respiratoryIllnessCase.setLoadDate(LocalDateTime.now());
                caseRepository.save(respiratoryIllnessCase);
                labRepository.saveAll(mapLabDtoToLab(m.getLabDtoList(), respiratoryIllnessCase));
                complaintRepository.saveAll(mapComplaintDtoToComplaint(m.getComplaintDtoList(), respiratoryIllnessCase));
                diagnosisRepository.saveAll(mapDiagnosisDtoToDiagnosis(m.getDiagnosis(), respiratoryIllnessCase));
            }
            batchIdList.add(caseMessageDto.getBatchId());
        });
        batchIdList.forEach(batchService::updateBatchOperation);
    }

}
