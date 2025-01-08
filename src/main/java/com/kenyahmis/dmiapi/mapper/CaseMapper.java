package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.model.IllnessCase;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {ComplaintMapper.class, DiagnosisMapper.class, FlaggedConditionMapper.class, LabMapper.class,
        RiskFactorMapper.class, SubjectMapper.class, VaccinationMapper.class, VitalSignMapper.class, ARTLinkageMapper.class}
        , componentModel = "spring"
)
public abstract class CaseMapper {
    ComplaintMapper complaintMapper = Mappers.getMapper(ComplaintMapper.class);
    DiagnosisMapper diagnosisMapper = Mappers.getMapper(DiagnosisMapper.class);
    FlaggedConditionMapper flaggedConditionMapper = Mappers.getMapper(FlaggedConditionMapper.class);
    LabMapper labMapper = Mappers.getMapper(LabMapper.class);
    RiskFactorMapper riskFactorMapper = Mappers.getMapper(RiskFactorMapper.class);
    SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);
    VaccinationMapper vaccinationMapper = Mappers.getMapper(VaccinationMapper.class);
    VitalSignMapper vitalSignMapper = Mappers.getMapper(VitalSignMapper.class);

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
    @Mapping(source = "caseDto.artLinkages", target = "artLinkages")
    @Mapping(target = "loadDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batchId", ignore = true)
    @Mapping(target = "emrId", ignore = true)
    public abstract IllnessCase caseDtoToIllnessCase(CaseDto caseDto, IllnessCase savedIllnessCase);

    @Mapping(source = "illnessCase.visitUniqueId", target = "caseUniqueId")
    @Mapping(source = "illnessCase.mflCode", target = "hospitalIdNumber")
    @InheritInverseConfiguration
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
        illnessCase.getArtLinkages().forEach(artLinkage -> artLinkage.setIllnessCase(sourceOfIds));
        illnessCase.setLoadDate(LocalDateTime.now());
    }
    public abstract List<CaseDto> caseToCaseDto(List<IllnessCase> illnessCases);

    public Bundle caseToBundle(IllnessCase illnessCase) {
        if (illnessCase == null) {
            return null;
        }
        List<Bundle.BundleEntryComponent> bundleEntryComponents = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.DOCUMENT);
        bundle.setId(illnessCase.getId().toString());
        Bundle.BundleEntryComponent compositionEntry = new Bundle.BundleEntryComponent();
        Composition composition = new Composition();
        composition.setId(illnessCase.getId().toString());
        CodeableConcept compositionType = new CodeableConcept();
        Coding compositionTypeCoding = new Coding();
        compositionTypeCoding.setSystem("http://loinc.org");
        compositionTypeCoding.setCode("55751-2");
        compositionTypeCoding.setDisplay("Public Health Case Report");
        compositionType.setCoding(List.of(compositionTypeCoding));
        composition.setType(compositionType);
        composition.setTitle("Initial Public Health Case Report");
        Reference subjectReference = new Reference();
        subjectReference.setDisplay("Case Report Patient");
        subjectReference.setReference("Patient/"+illnessCase.getSubject().getId().toString());
        composition.setSubject(subjectReference);

        Reference custodianReference = new Reference();
        custodianReference.setDisplay(illnessCase.getMflCode());
        custodianReference.setReference("Organization/"+illnessCase.getMflCode());
        composition.setCustodian(custodianReference);

        // complaints composition section
        Composition.SectionComponent complaintsSectionComponent = new Composition.SectionComponent();
        complaintsSectionComponent.setTitle("Complaints");
        List<Reference> complaintsReferences = new ArrayList<>();
        illnessCase.getComplaints().forEach(complaint -> {
            Reference reference = new Reference();
            reference.setReference("Observation/" + complaint.getId().toString());
            reference.setDisplay(complaint.getComplaint());
            complaintsReferences.add(reference);
        });
        complaintsSectionComponent.setEntry(complaintsReferences);
        // diagnosis section
        Composition.SectionComponent diagnosisSectionComponent = new Composition.SectionComponent();
        diagnosisSectionComponent.setTitle("Diagnosis");
        List<Reference> diagnosisReferences = new ArrayList<>();
        illnessCase.getDiagnosis().forEach(diagnosis -> {
            Reference reference = new Reference();
            reference.setReference("Observation/" + diagnosis.getId().toString());
            reference.setDisplay(diagnosis.getDiagnosis());
            diagnosisReferences.add(reference);
        });
        diagnosisSectionComponent.setEntry(diagnosisReferences);
        // lab section
        Composition.SectionComponent labSectionComponent = new Composition.SectionComponent();
        labSectionComponent.setTitle("Laboratory");
        List<Reference> labReferences = new ArrayList<>();
        illnessCase.getLabs().forEach(lab -> {
            Reference reference = new Reference();
            reference.setReference("Observation/" + lab.getId().toString());
            reference.setDisplay(lab.getTestResult());
            labReferences.add(reference);
        });
        labSectionComponent.setEntry(labReferences);
        // vital signs section
        Composition.SectionComponent vitalSignsSectionComponent = new Composition.SectionComponent();
        List<Reference> vitalSignReferences= illnessCase.getVitalSigns().stream().map(vitalSign -> {
            Reference reference = new Reference();
            reference.setReference("Observation/" + vitalSign.getId().toString());
            reference.setDisplay(String.format("Temperature: %f celsius - %s, Respiratory rate: %d, Oxygen Saturation: %d - %s",
                    vitalSign.getTemperature(), vitalSign.getTemperatureMode(), vitalSign.getRespiratoryRate(),
                    vitalSign.getOxygenSaturation(), vitalSign.getOxygenSaturationMode()));
            return reference;
        }).toList();
        vitalSignsSectionComponent.setEntry(vitalSignReferences);
        // risk factor section
        Composition.SectionComponent riskFactorsSectionComponent = new Composition.SectionComponent();
        List<Reference> riskFactorReferences = illnessCase.getRiskFactors().stream().map(riskFactor -> {
            Reference reference = new Reference();
            reference.setReference("Observation/" + riskFactor.getId().toString());
            reference.setDisplay(riskFactor.getCondition());
            return reference;
        }).toList();
        riskFactorsSectionComponent.setEntry(riskFactorReferences);
        // vaccination section
        Composition.SectionComponent vaccinationsSectionComponent = new Composition.SectionComponent();
        List<Reference> vaccinationReferences = illnessCase.getVaccinations().stream().map(vaccination -> {
            Reference reference = new Reference();
            reference.setReference("Immunization/" + vaccination.getId().toString());
            reference.setDisplay(vaccination.getVaccination());
            return reference;
        }).toList();
        vaccinationsSectionComponent.setEntry(vaccinationReferences);
        // flagged conditions
        Composition.SectionComponent flaggedConditionsSegment = new Composition.SectionComponent();
        List<Reference> flaggedConditionsReferences = illnessCase.getFlaggedConditions().stream().map(condition -> {
            Reference reference = new Reference();
            reference.setReference("Condition/" + condition.getId().toString());
            reference.setDisplay(condition.getConditionName());
            return reference;
        }).toList();
        flaggedConditionsSegment.setEntry(flaggedConditionsReferences);
        composition.setSection(List.of(complaintsSectionComponent));
        compositionEntry.setResource(composition);

        bundleEntryComponents.add(compositionEntry);
        bundleEntryComponents.addAll(complaintMapper.complaintToBundleEntryComponent(illnessCase.getComplaints()));
        bundleEntryComponents.addAll(diagnosisMapper.diagnosisToBundleEntryComponent(illnessCase.getDiagnosis()));
        bundleEntryComponents.addAll(vaccinationMapper.vaccinationToBundleEntryComponents(illnessCase.getVaccinations()));
        bundleEntryComponents.addAll(vitalSignMapper.vitalSignToBundleEntryComponents(illnessCase.getVitalSigns()));
        bundleEntryComponents.addAll(riskFactorMapper.riskFactorToBundleEntryComponent(illnessCase.getRiskFactors()));
        bundleEntryComponents.addAll(labMapper.labToBundleEntryComponent(illnessCase.getLabs()));
        bundleEntryComponents.addAll(diagnosisMapper.diagnosisToBundleEntryComponent(illnessCase.getDiagnosis()));
        bundleEntryComponents.addAll(flaggedConditionMapper.flaggedConditionToBundleEntryComponent(illnessCase.getFlaggedConditions()));
        bundleEntryComponents.add(subjectMapper.subjectToBundleEntryComponent(illnessCase.getSubject()));
        bundle.setEntry(bundleEntryComponents);
        return bundle;
    }

    public Bundle caseListToBundle(List<IllnessCase> cases) {
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.SEARCHSET);
        List<Bundle.BundleEntryComponent> bundleEntryComponents = new ArrayList<>();
        cases.forEach(illnessCase -> {
            Bundle.BundleEntryComponent bundleEntryComponent = new Bundle.BundleEntryComponent();
            bundleEntryComponent.setResource(caseToBundle(illnessCase));
            bundleEntryComponents.add(bundleEntryComponent);
        });
        bundle.setEntry(bundleEntryComponents);
        return bundle;
    }

}
