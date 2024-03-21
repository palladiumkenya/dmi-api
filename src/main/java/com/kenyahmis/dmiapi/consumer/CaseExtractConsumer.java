package com.kenyahmis.dmiapi.consumer;

import com.kenyahmis.dmiapi.dto.*;
import com.kenyahmis.dmiapi.model.*;
import com.kenyahmis.dmiapi.repository.*;
import com.kenyahmis.dmiapi.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CaseExtractConsumer {

    private final CaseRepository caseRepository;
    private final LabRepository labRepository;
    private final ComplaintRepository complaintRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final RiskFactorRepository riskFactorRepository;
    private final VitalSignRepository vitalSignRepository;
    private final VaccinationRepository vaccinationRepository;
    private final SubjectRepository subjectRepository;
    private final EmrRepository emrRepository;
    private final FlaggedConditionRepository flaggedConditionRepository;

    private final BatchService batchService;
    private final Logger LOGGER = LoggerFactory.getLogger(CaseExtractConsumer.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CaseExtractConsumer(CaseRepository caseRepository, LabRepository labRepository, BatchService batchService,
                               ComplaintRepository complaintRepository, DiagnosisRepository diagnosisRepository,
                               RiskFactorRepository riskFactorRepository, VitalSignRepository vitalSignRepository,
                               VaccinationRepository vaccinationRepository, SubjectRepository subjectRepository,
                               EmrRepository emrRepository, FlaggedConditionRepository flaggedConditionRepository) {
        this.caseRepository = caseRepository;
        this.labRepository = labRepository;
        this.batchService = batchService;
        this.complaintRepository = complaintRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.vitalSignRepository = vitalSignRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.riskFactorRepository = riskFactorRepository;
        this.subjectRepository = subjectRepository;
        this.emrRepository = emrRepository;
        this.flaggedConditionRepository = flaggedConditionRepository;
    }

    private List<RiskFactor> mapRiskFactorDtoToRiskFactor(List<RiskFactorDto> riskFactorDtoList, Case illnessCase) {
        List<RiskFactor> riskFactorList = new ArrayList<>();
        riskFactorDtoList.forEach(riskFactorDto -> {
            RiskFactor riskFactor = new RiskFactor();
            riskFactor.setCaseId(illnessCase.getId());
            riskFactor.setRiskFactorId(riskFactorDto.getRiskFactorId());
            riskFactor.setCondition(riskFactorDto.getCondition());
            riskFactor.setVoided(riskFactorDto.getVoided());
            riskFactorList.add(riskFactor);
        });
        return riskFactorList;
    }

    private Subject mapSubjectDtoToSubject(SubjectDto subjectDto) {
        LocalDateTime dob  = subjectDto.getDateOfBirth() == null ? null : LocalDateTime.parse(subjectDto.getDateOfBirth(), formatter);
        Subject subject = new Subject();
        subject.setAddress(subjectDto.getAddress());
        subject.setNupi(subjectDto.getNupi());
        subject.setPatientUniqueId(subjectDto.getPatientUniqueId());
        subject.setSex(subjectDto.getSex());
        subject.setDateOfBirth(dob);
        subject.setCounty(subjectDto.getCounty());
        subject.setSubCounty(subjectDto.getSubCounty());
        return subject;
    }
    private List<VitalSign> mapVitalSignDtoToVitalSign(List<VitalSignsDto> vitalSignsDtoList, Case illnessCase) {
        List<VitalSign> vitalSignList = new ArrayList<>();
        vitalSignsDtoList.forEach(vitalSignsDto -> {
            VitalSign vitalSign = new VitalSign();
            vitalSign.setVitalSignId(vitalSignsDto.getVitalSignId());
            vitalSign.setVitalSignDate(LocalDateTime.parse(vitalSignsDto.getVitalSignDate(), formatter));
            vitalSign.setCaseId(illnessCase.getId());
            vitalSign.setTemperature(vitalSignsDto.getTemperature());
            vitalSign.setTemperatureMode(vitalSignsDto.getTemperatureMode());
            vitalSign.setOxygenSaturation(vitalSignsDto.getOxygenSaturation());
            vitalSign.setOxygenSaturationMode(vitalSignsDto.getOxygenSaturationMode());
            vitalSign.setVoided(vitalSignsDto.getVoided());
            vitalSignList.add(vitalSign);
        });
        return vitalSignList;
    }

    private List<Vaccination> mapVaccinationDtoToVaccination(List<VaccinationDto> vaccinationDtoList, Case illnessCase) {
        List<Vaccination> vaccinationList = new ArrayList<>();
        vaccinationDtoList.forEach(vaccinationDto -> {
            Vaccination vaccination = new Vaccination();
            vaccination.setCaseId(illnessCase.getId());
            vaccination.setVaccinationId(vaccinationDto.getVaccinationId());
            vaccination.setVaccination(vaccinationDto.getVaccination());
            vaccination.setDoses(vaccinationDto.getDoses());
            vaccination.setVerified(vaccinationDto.getVerified());
            vaccination.setVoided(vaccinationDto.getVoided());
            vaccinationList.add(vaccination);
        });
        return vaccinationList;
    }
    private List<Lab> mapLabDtoToLab(List<LabDto> labDtoList, Case illnessCase)  {
        List<Lab> labList = new ArrayList<>();
        labDtoList.forEach(labDto->{
            LocalDateTime labDate = labDto.getLabDate() == null ? null : LocalDateTime.parse(labDto.getLabDate(), formatter);
            Lab lab = new Lab();
            lab.setLabDate(labDate);
            lab.setOrderId(labDto.getOrderId());
            lab.setTestName(labDto.getTestName());
            lab.setTestResult(labDto.getTestResult());
            lab.setVoided(labDto.getVoided());
            lab.setCaseId(illnessCase.getId());
            labList.add(lab);
        });
        return labList;
    }

    private List<Complaint> mapComplaintDtoToComplaint(List<ComplaintDto> complaintDtoList, Case illnessCase) {
        List<Complaint> complaintList = new ArrayList<>();
        complaintDtoList.forEach(complaintDto -> {
            LocalDate onsetDate = complaintDto.getOnsetDate() == null ? null : LocalDateTime.parse(complaintDto.getOnsetDate(), formatter).toLocalDate();
            Complaint complaint = new Complaint();
            complaint.setComplaintId(complaintDto.getComplaintId());
            complaint.setComplaint(complaintDto.getComplaint());
            complaint.setOnsetDate(onsetDate);
            complaint.setDuration(complaintDto.getDuration());
            complaint.setVoided(complaintDto.getVoided());
            complaint.setCaseId(illnessCase.getId());
            complaintList.add(complaint);
        });
        return complaintList;
    }

    private List<FlaggedCondition> mapFlaggedConditionDtoToFlaggedCondition(List<FlaggedConditionDto> flaggedConditionDtoList, Case illnessCase) {
        List<FlaggedCondition> flaggedConditionList = new ArrayList<>();
        flaggedConditionDtoList.forEach(flaggedConditionDto -> {
            FlaggedCondition flaggedCondition = new FlaggedCondition();
            flaggedCondition.setConditionId(flaggedConditionDto.getConditionId());
            flaggedCondition.setConditionName(flaggedConditionDto.getConditionName().toUpperCase());
            flaggedCondition.setVoided(flaggedConditionDto.getVoided());
            flaggedCondition.setCaseId(illnessCase.getId());
            flaggedConditionList.add(flaggedCondition);
        });
        return flaggedConditionList;
    }

    private List<Diagnosis> mapDiagnosisDtoToDiagnosis(List<DiagnosisDto> diagnosisDtoList, Case illnessCase) {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisDtoList.forEach(diagnosisDto -> {
            LocalDateTime diagnosisDate = diagnosisDto.getDiagnosisDate() == null ? null : LocalDateTime.parse(diagnosisDto.getDiagnosisDate(), formatter);
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setDiagnosisId(diagnosisDto.getDiagnosisId());
            diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
            diagnosis.setDiagnosisDate(diagnosisDate);
            diagnosis.setVoided(diagnosisDto.getVoided());
            diagnosis.setCaseId(illnessCase.getId());
            diagnosis.setSystem(diagnosisDto.getSystem());
            diagnosis.setSystemCode(diagnosis.getSystemCode());
            diagnosisList.add(diagnosis);
        });
        return diagnosisList;
    }
    private void updateLabs(List<LabDto> labDtoList, Case illnessCase) {
        List<Lab> labList = new ArrayList<>();
        labDtoList.forEach(labDto -> {
            Optional<Lab> optionalLab = labRepository.findByCaseIdAndOrderId(illnessCase.getId(), labDto.getOrderId());
            if (optionalLab.isPresent()){
                Lab lab = optionalLab.get();
                lab.setTestResult(labDto.getTestResult());
                lab.setTestName(labDto.getTestName());
                lab.setLabDate(LocalDateTime.parse(labDto.getLabDate(), formatter));
                lab.setVoided(labDto.getVoided());
                labList.add(lab);
            } else {
                Lab lab = new Lab();
                lab.setLabDate(LocalDateTime.parse(labDto.getLabDate(), formatter));
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

    private void updateVaccinations(List<VaccinationDto> vaccinationDtoList, Case illnessCase) {
        List<Vaccination> vaccinationList = new ArrayList<>();
        vaccinationDtoList.forEach(vaccinationDto -> {
            Optional<Vaccination> optionalVaccination = vaccinationRepository.findByCaseIdAndVaccinationId(illnessCase.getId(), vaccinationDto.getVaccinationId());
            Vaccination vaccination;
            if (optionalVaccination.isPresent()) {
                vaccination = optionalVaccination.get();
                vaccination.setVoided(vaccinationDto.getVoided());
                vaccination.setVerified(vaccinationDto.getVerified());
                vaccination.setVaccination(vaccinationDto.getVaccination());
                vaccination.setDoses(vaccinationDto.getDoses());
            } else {
                vaccination = new Vaccination();
                vaccination.setCaseId(illnessCase.getId());
                vaccination.setVaccinationId(vaccinationDto.getVaccinationId());
                vaccination.setVaccination(vaccinationDto.getVaccination());
                vaccination.setDoses(vaccinationDto.getDoses());
                vaccination.setVerified(vaccinationDto.getVerified());
                vaccination.setVoided(vaccinationDto.getVoided());
            }
            vaccinationList.add(vaccination);
        });
        vaccinationRepository.saveAll(vaccinationList);
    }

    private void updateSubject(SubjectDto subjectDto, Case illnessCase) {
        Optional<Subject> optionalSubject = subjectRepository.findById(illnessCase.getSubjectId());
        Subject subject;
        if (optionalSubject.isPresent()) {
            subject = optionalSubject.get();
        } else {
            subject = new Subject();
            subject.setPatientUniqueId(subjectDto.getPatientUniqueId());
        }
        subject.setAddress(subjectDto.getAddress());
        subject.setNupi(subject.getNupi());
        subject.setSex(subjectDto.getSex());
        subject.setDateOfBirth(LocalDateTime.parse(subjectDto.getDateOfBirth(), formatter));
        subject.setCounty(subject.getCounty());
        subject.setSubCounty(subjectDto.getSubCounty());
        subjectRepository.save(subject);
    }
    private void updateRiskFactors(List<RiskFactorDto> riskFactorDtoList, Case illnessCase) {
        List<RiskFactor> riskFactorList = new ArrayList<>();
        riskFactorDtoList.forEach(riskFactorDto -> {
            Optional<RiskFactor> optionalRiskFactor = riskFactorRepository.findByCaseIdAndRiskFactorId(illnessCase.getId(), riskFactorDto.getRiskFactorId());
            RiskFactor riskFactor;
            if (optionalRiskFactor.isPresent()) {
                riskFactor = optionalRiskFactor.get();
            } else {
                riskFactor = new RiskFactor();
                riskFactor.setCaseId(illnessCase.getId());
                riskFactor.setRiskFactorId(riskFactorDto.getRiskFactorId());
            }
            riskFactor.setCondition(riskFactorDto.getCondition());
            riskFactor.setVoided(riskFactorDto.getVoided());
            riskFactorList.add(riskFactor);
            riskFactorRepository.saveAll(riskFactorList);
        });
    }

    private void updateVitalSigns(List<VitalSignsDto> vitalSignsDtoList, Case illnessCase) {
        List<VitalSign> vitalSignList = new ArrayList<>();
        vitalSignsDtoList.forEach(vitalSignsDto -> {
            Optional<VitalSign> optionalVitalSign = vitalSignRepository.findByCaseIdAndVitalSignId(illnessCase.getId(), vitalSignsDto.getVitalSignId());
            VitalSign vitalSign;
            if (optionalVitalSign.isPresent()) {
                vitalSign = optionalVitalSign.get();
            } else {
                vitalSign = new VitalSign();
                vitalSign.setVitalSignId(vitalSignsDto.getVitalSignId());
                vitalSign.setCaseId(illnessCase.getId());
            }
            vitalSign.setVitalSignDate(LocalDateTime.parse(vitalSignsDto.getVitalSignDate(), formatter));
            vitalSign.setTemperature(vitalSignsDto.getTemperature());
            vitalSign.setTemperatureMode(vitalSignsDto.getTemperatureMode());
            vitalSign.setOxygenSaturation(vitalSignsDto.getOxygenSaturation());
            vitalSign.setOxygenSaturationMode(vitalSignsDto.getOxygenSaturationMode());
            vitalSign.setVoided(vitalSignsDto.getVoided());
            vitalSignList.add(vitalSign);
        });
        vitalSignRepository.saveAll(vitalSignList);
    }
    private void updateComplaints(List<ComplaintDto> complaintDtoList, Case illnessCase) {
        List<Complaint> complaintList = new ArrayList<>();
        complaintDtoList.forEach(complaintDto -> {
            Optional<Complaint> optionalLab = complaintRepository.findByCaseIdAndComplaintId(illnessCase.getId(), complaintDto.getComplaintId());
            Complaint complaint;
            if (optionalLab.isPresent()){
                complaint = optionalLab.get();
                complaint.setComplaint(complaintDto.getComplaint());
                complaint.setOnsetDate(LocalDateTime.parse(complaintDto.getOnsetDate(), formatter).toLocalDate());
                complaint.setVoided(complaintDto.getVoided());
                complaint.setDuration(complaintDto.getDuration());
            }else {
                complaint = new Complaint();
                complaint.setComplaintId(complaintDto.getComplaintId());
                complaint.setComplaint(complaintDto.getComplaint());
                complaint.setCaseId(illnessCase.getId());
                complaint.setOnsetDate(LocalDateTime.parse(complaintDto.getOnsetDate(), formatter).toLocalDate());
                complaint.setDuration(complaintDto.getDuration());
                complaint.setVoided(complaintDto.getVoided());
            }
            complaintList.add(complaint);
        });
        complaintRepository.saveAll(complaintList);
    }

    private void updateFlaggedConditions(List<FlaggedConditionDto> flaggedConditionDtoList, Case illnessCase) {
        List<FlaggedCondition> flaggedConditionList = new ArrayList<>();
        flaggedConditionDtoList.forEach(flaggedConditionDto -> {
            Optional<FlaggedCondition> optionalFlaggedCondition = flaggedConditionRepository
                    .findByCaseIdAndConditionId(illnessCase.getId(), flaggedConditionDto.getConditionId());
            FlaggedCondition flaggedCondition;
            if (optionalFlaggedCondition.isPresent()){
                flaggedCondition = optionalFlaggedCondition.get();
                flaggedCondition.setVoided(flaggedConditionDto.getVoided());
                flaggedCondition.setConditionName(flaggedConditionDto.getConditionName().toUpperCase());
            } else {
                flaggedCondition = new FlaggedCondition();
                flaggedCondition.setCaseId(illnessCase.getId());
                flaggedCondition.setConditionName(flaggedConditionDto.getConditionName().toUpperCase());
                flaggedCondition.setConditionId(flaggedConditionDto.getConditionId());
                flaggedCondition.setVoided(flaggedConditionDto.getVoided());
            }
            flaggedConditionList.add(flaggedCondition);
        });
        flaggedConditionRepository.saveAll(flaggedConditionList);
    }

    private void updateDiagnosis(List<DiagnosisDto> diagnosisDtoList, Case illnessCase) {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisDtoList.forEach(diagnosisDto -> {
            Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findByCaseIdAndDiagnosisId(illnessCase.getId(), diagnosisDto.getDiagnosisId());
            Diagnosis diagnosis;
            if (optionalDiagnosis.isPresent()){
                diagnosis = optionalDiagnosis.get();
                diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
                diagnosis.setDiagnosisDate(LocalDateTime.parse(diagnosisDto.getDiagnosisDate(), formatter));
                diagnosis.setVoided(diagnosisDto.getVoided());
                diagnosis.setSystemCode(diagnosisDto.getSystemCode());
                diagnosis.setSystem(diagnosisDto.getSystem());
            }else {
                diagnosis = new Diagnosis();
                diagnosis.setDiagnosisId(diagnosisDto.getDiagnosisId());
                diagnosis.setDiagnosis(diagnosisDto.getDiagnosis());
                diagnosis.setDiagnosisDate(LocalDateTime.parse(diagnosisDto.getDiagnosisDate(), formatter));
                diagnosis.setVoided(diagnosisDto.getVoided());
                diagnosis.setCaseId(illnessCase.getId());
                diagnosis.setSystemCode(diagnosisDto.getSystemCode());
                diagnosis.setSystem(diagnosisDto.getSystem());
            }
            diagnosisList.add(diagnosis);
        });
        diagnosisRepository.saveAll(diagnosisList);
    }

    @KafkaListener(id = "visitListener", topics = "visitTopic", containerFactory = "kafkaListenerContainerFactory")
    public void listenToMessage(List<CaseMessageDto> messages) {
        Set<UUID> batchIdList = new HashSet<>();
        messages.forEach(caseMessageDto -> {
            CaseDto m = caseMessageDto.getCaseDto();
            Optional<Case>  optionalRespiratoryIllnessCase = caseRepository.findByVisitUniqueIdAndMflCode(m.getCaseUniqueId(), m.getHospitalIdNumber());
            Case aCase;
            LocalDate admissionDate = m.getAdmissionDate() == null ? null : LocalDateTime.parse(m.getAdmissionDate(), formatter).toLocalDate();
            LocalDate outpatientDate = m.getOutpatientDate() == null ? null : LocalDateTime.parse(m.getOutpatientDate(), formatter).toLocalDate();
            LocalDateTime finalOutcomeDate = m.getFinalOutcomeDate() == null ? null : LocalDateTime.parse(m.getFinalOutcomeDate(), formatter);
            LocalDateTime createdAt = m.getCreatedAt() == null ? null : LocalDateTime.parse(m.getCreatedAt(), formatter);
            LocalDateTime updatedAt = m.getUpdatedAt() == null ? null : LocalDateTime.parse(m.getUpdatedAt(), formatter);

            if (optionalRespiratoryIllnessCase.isPresent()) {
                // updated case
                aCase = optionalRespiratoryIllnessCase.get();
                aCase.setStatus(m.getStatus());
                aCase.setFinalOutcome(m.getFinalOutcome());
                aCase.setFinalOutcomeDate(finalOutcomeDate);
                aCase.setVisitUniqueId(m.getCaseUniqueId());
                aCase.setMflCode(m.getHospitalIdNumber());
                aCase.setAdmissionDate(admissionDate);
                aCase.setOutpatientDate(outpatientDate);
                aCase.setCreatedAt(createdAt);
                aCase.setUpdatedAt(updatedAt);
                aCase.setLoadDate(LocalDateTime.now());
                caseRepository.save(aCase);

                // update complaints
                updateComplaints(m.getComplaintDtoList(), aCase);

                // update labs
                updateLabs(m.getLabDtoList(), aCase);

                //update diagnosis
                updateDiagnosis(m.getDiagnosis(), aCase);

                //update vital signs
                updateVitalSigns(m.getVitalSigns(), aCase);

                //update risk factors
                updateRiskFactors(m.getRiskFactorDtoList(), aCase);

                // update vaccinations
                updateVaccinations(m.getVaccinationDtoList(), aCase);

                // update subject
                updateSubject(m.getSubject(), aCase);

                // update flagged conditions
                updateFlaggedConditions(m.getFlaggedConditionDtoList(), aCase);
            } else {
                // created new case
                UUID emrId = null;
                Optional<Emr> emr = emrRepository.findByEmrNameAndVoided(caseMessageDto.getEmr(), false);
                if (emr.isPresent()) {
                    emrId = emr.get().getId();
                }
                aCase = new Case();
                aCase.setBatchId(caseMessageDto.getBatchId());
                aCase.setEmrId(emrId);
                aCase.setStatus(m.getStatus());
                aCase.setFinalOutcome(m.getFinalOutcome());
                aCase.setFinalOutcomeDate(finalOutcomeDate);
                aCase.setVisitUniqueId(m.getCaseUniqueId());
                aCase.setMflCode(m.getHospitalIdNumber());
                aCase.setAdmissionDate(admissionDate);
                aCase.setOutpatientDate(outpatientDate);
                aCase.setCreatedAt(createdAt);
                aCase.setUpdatedAt(updatedAt);
                aCase.setLoadDate(LocalDateTime.now());
                Subject subject = subjectRepository.save(mapSubjectDtoToSubject(m.getSubject()));
                aCase.setSubjectId(subject.getId());
                caseRepository.save(aCase);
                labRepository.saveAll(mapLabDtoToLab(m.getLabDtoList(), aCase));
                complaintRepository.saveAll(mapComplaintDtoToComplaint(m.getComplaintDtoList(), aCase));
                diagnosisRepository.saveAll(mapDiagnosisDtoToDiagnosis(m.getDiagnosis(), aCase));
                vitalSignRepository.saveAll(mapVitalSignDtoToVitalSign(m.getVitalSigns(), aCase));
                vaccinationRepository.saveAll(mapVaccinationDtoToVaccination(m.getVaccinationDtoList(), aCase));
                riskFactorRepository.saveAll(mapRiskFactorDtoToRiskFactor(m.getRiskFactorDtoList(), aCase));
                flaggedConditionRepository.saveAll(mapFlaggedConditionDtoToFlaggedCondition(m.getFlaggedConditionDtoList(), aCase));
            }
            batchIdList.add(caseMessageDto.getBatchId());
        });
        batchIdList.forEach(batchService::updateBatchOperation);
    }
}
