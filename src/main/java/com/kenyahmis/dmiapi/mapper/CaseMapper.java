package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.model.IllnessCase;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(uses = {ComplaintMapper.class, DiagnosisMapper.class, FlaggedConditionMapper.class, LabMapper.class,
        RiskFactorMapper.class, SubjectMapper.class, VaccinationMapper.class, VitalSignMapper.class}
        , componentModel = "spring"
)
public abstract class CaseMapper {
    @Mapping(target = "visitUniqueId", source = "caseDto.caseUniqueId")
    @Mapping(target = "mflCode", source = "caseDto.hospitalIdNumber")
    @Mapping(target = "finalOutcomeDate", source = "caseDto.finalOutcomeDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "interviewDate", source = "caseDto.interviewDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "admissionDate", source = "caseDto.admissionDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "outpatientDate", source = "caseDto.outpatientDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdAt", source = "caseDto.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updatedAt", source = "caseDto.updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "caseDto.subject", target = "subject")
    @Mapping(source = "caseDto.status", target = "status")
    @Mapping(source = "caseDto.finalOutcome", target = "finalOutcome")
    @Mapping(source = "caseDto.riskFactors", target = "riskFactors")
    @Mapping(source = "caseDto.flaggedConditions", target = "flaggedConditions")
    @Mapping(source = "caseDto.vitalSigns", target = "vitalSigns")
    @Mapping(source = "caseDto.labs", target = "labs")
    @Mapping(source = "caseDto.diagnosis", target = "diagnosis")
    @Mapping(source = "caseDto.complaints", target = "complaints")
    @Mapping(source = "caseDto.vaccinations", target = "vaccinations")
    @Mapping(target = "loadDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batchId", ignore = true)
    @Mapping(target = "emrId", ignore = true)
    public abstract IllnessCase caseDtoToIllnessCase(CaseDto caseDto, IllnessCase savedIllnessCase);

    @Mapping(source = "illnessCase.visitUniqueId", target = "caseUniqueId")
    @Mapping(source = "illnessCase.mflCode", target = "hospitalIdNumber")
    public abstract CaseDto caseToCaseDto(IllnessCase illnessCase);

    @AfterMapping
    protected void setCaseId(@MappingTarget IllnessCase illnessCase, IllnessCase savedIllnessCase) {
        IllnessCase sourceOfIds;
        if (savedIllnessCase != null) {
            sourceOfIds = savedIllnessCase;
        } else {
            sourceOfIds = illnessCase;
        }
        illnessCase.getComplaints().forEach(complaint -> complaint.setIllnessCase(sourceOfIds));
        illnessCase.getDiagnosis().forEach(diagnosis -> diagnosis.setIllnessCase(sourceOfIds));
        illnessCase.getFlaggedConditions().forEach(flaggedCondition -> flaggedCondition.setIllnessCase(sourceOfIds));
        illnessCase.getLabs().forEach(lab -> lab.setIllnessCase(sourceOfIds));
        illnessCase.getVaccinations().forEach(vaccination -> vaccination.setIllnessCase(sourceOfIds));
        illnessCase.getVitalSigns().forEach(vitalSign -> vitalSign.setIllnessCase(sourceOfIds));
        illnessCase.getRiskFactors().forEach(riskFactor -> riskFactor.setIllnessCase(sourceOfIds));
        illnessCase.setLoadDate(LocalDateTime.now());
    }
}
