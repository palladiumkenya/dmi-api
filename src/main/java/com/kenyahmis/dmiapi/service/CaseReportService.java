package com.kenyahmis.dmiapi.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.parser.IParser;
import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.mapper.CaseMapper;
import com.kenyahmis.dmiapi.model.IllnessCase;
import com.kenyahmis.dmiapi.model.PageData;
import com.kenyahmis.dmiapi.repository.CaseRepository;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.codesystems.ConditionCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CaseReportService {
    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;
    private final Logger LOG = LoggerFactory.getLogger(CaseReportService.class);

    public CaseReportService(CaseRepository caseRepository, CaseMapper caseMapper) {
        this.caseRepository = caseRepository;
        this.caseMapper = caseMapper;
    }

//    public List<IllnessCase> getReports() {
//        return caseRepository.findAll();
//    }

    public Page<CaseDto> getReports(String startDate, String endDate, Pageable pageable) {
        Page<IllnessCase> casePage = caseRepository.findAll(pageable);
        return new PageData<>(caseMapper.caseToCaseDto(casePage.getContent()), casePage.getPageable(), casePage.getTotalElements());
    }

    public String getCaseReport(UUID caseId) {

        String response = null;
        IllnessCase illnessCase = caseRepository.findById(caseId).orElse(null);
        if (illnessCase != null) {
            FhirContext ctx = FhirContext.forR4();
            IParser parser = ctx.newJsonParser();
            parser.setPrettyPrint(true);
            Bundle bundle = new Bundle();
            bundle.setType(Bundle.BundleType.DOCUMENT);
            bundle.setId(caseId.toString());
            Bundle.BundleEntryComponent compositionEntry = new Bundle.BundleEntryComponent();
            Composition composition = new Composition();
//            composition.setAuthor(new ArrayList<>().add());
            composition.setId(caseId.toString());
//            composition.setIdentifier(new Identifier().setValue());
            composition.setStatus(Composition.CompositionStatus.FINAL);
            Extension extension = new Extension();

            CodeableConcept compositionType = new CodeableConcept();
            Coding compositionTypeCoding = new Coding();
            compositionTypeCoding.setSystem("http://loinc.org");
            compositionTypeCoding.setCode("55751-2");
            compositionTypeCoding.setDisplay("Public Health Case Report");
            compositionType.setCoding(List.of(compositionTypeCoding));
            composition.setType(compositionType);
            composition.setTitle("Initial Public Health Case Report");

            Reference subjectReference = new Reference();
            subjectReference.setDisplay("Test Patient");
            subjectReference.setReference("Patient/"+illnessCase.getSubject().getId().toString());
            composition.setSubject(subjectReference);

            Reference custodianReference = new Reference();
            custodianReference.setDisplay("Test Patient");
            custodianReference.setReference("Organization/"+illnessCase.getMflCode());
            composition.setCustodian(custodianReference);

            //history of illness
//            composition.setSection();
            Composition.SectionComponent sectionComponent = new Composition.SectionComponent();
//            sectionComponent.setText();
//            sectionComponent.setTitle();

            // conditions
//            sectionComponent.setEntry();
//            Reference reference = new Reference();
//            reference.setReference("");
            compositionEntry.setResource(composition);



            // Condition resource TODO do the same for flagged condition
            Condition condition = new Condition();
            condition.setId(caseId.toString());
            DateType dateType = new DateType(Date.valueOf(LocalDate.now()));
//            condition.setOnset(dateType);
            //
//            condition.setCode();
            CodeableConcept conditionType = new CodeableConcept();
            Coding conditionTypeCoding = new Coding();
            conditionTypeCoding.setSystem("http://loinc.org");
            conditionTypeCoding.setSystem("http://loinc.org");
            conditionTypeCoding.setCode("http://loinc.org");
            conditionTypeCoding.setDisplay("");
            conditionType.setCoding(List.of(conditionTypeCoding));
            condition.setCategory(List.of(conditionType));
            Bundle.BundleEntryComponent complaintEntry = new Bundle.BundleEntryComponent();
            complaintEntry.setResource(condition);

            // immunization details
            Immunization immunization = new Immunization();
            immunization.setId(caseId.toString());
            immunization.setStatus(Immunization.ImmunizationStatus.COMPLETED);

            // vaccine code
            CodeableConcept vaccineCodeConcept = new CodeableConcept();
            Coding vaccineCode = new Coding();
            vaccineCode.setSystem("http://hl7.org/fhir/sid/cvx");
            vaccineCode.setCode("56");
            vaccineCode.setDisplay("Dengue Fever");
            vaccineCodeConcept.setCoding(List.of(vaccineCode));
            vaccineCode.setDisplay("");
            vaccineCodeConcept.setText("");
            immunization.setVaccineCode(vaccineCodeConcept);
//            immunization.setOccurrence(DateType.today());
            Extension doseExtension = new Extension();
            doseExtension.setValue(new StringType("2"));
            doseExtension.setUrl("http://example.com/fhir/StructureDefinition/my-extension");
            immunization.setExtension(List.of(doseExtension));
            Bundle.BundleEntryComponent immunizationEntry = new Bundle.BundleEntryComponent();
            immunizationEntry.setResource(immunization);

            // vital signs (temperature, respiratory rate, oxygen saturation)
            Bundle.BundleEntryComponent temperatureEntry = new Bundle.BundleEntryComponent();
            Observation temperatureObservation = new Observation();
            temperatureObservation.setId(caseId.toString());
            temperatureObservation.setStatus(Observation.ObservationStatus.FINAL);
//            temperatureObservation.setCategory();
//            temperatureObservation.setCode();

            System.out.println("================================");
            System.out.println(Date.valueOf(LocalDate.now()));
//            temperatureObservation.setEffective();
//            temperatureObservation.setEffective(DateTimeType.today());
            temperatureObservation.setEffective(DateTimeType.today());
            DateTimeType dateTimeType = new DateTimeType(Date.valueOf(LocalDate.now()));
            dateTimeType.setPrecision(TemporalPrecisionEnum.DAY);
            temperatureObservation.setEffective(dateTimeType);

//            temperatureObservation.setEffective(new DateType(Date.valueOf(LocalDate.now())));
//            Quantity temperatureQuantity = new Quantity();
//            temperatureQuantity.setValue(39.0);
//            temperatureQuantity.setUnit("celsius");
//            temperatureQuantity.setSystem("http://loinc.org");
//            temperatureQuantity.setCode("celsius");

            StringType stringType = new StringType();
            stringType.setValue("Sample value");
            temperatureObservation.setValue(stringType);
//            temperatureObservation.setValue(temperatureQuantity);
            temperatureEntry.setResource(temperatureObservation);

            // lab results
            Observation labResult = new Observation();
            labResult.setId(caseId.toString());
            labResult.setStatus(Observation.ObservationStatus.FINAL);
//            labResult.setCategory();
//            labResult.setCode();
//            labResult.setEffective();
//

            bundle.setEntry(List.of(compositionEntry, complaintEntry, immunizationEntry,temperatureEntry));
            response =  parser.encodeResourceToString(bundle);
            LOG.info("Response: {}", response);
        }
       return response;
    }
}
