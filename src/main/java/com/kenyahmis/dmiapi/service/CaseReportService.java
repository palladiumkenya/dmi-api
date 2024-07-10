package com.kenyahmis.dmiapi.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.kenyahmis.dmiapi.model.IllnessCase;
import com.kenyahmis.dmiapi.repository.CaseRepository;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CaseReportService {
    private final CaseRepository caseRepository;
    private final Logger LOG = LoggerFactory.getLogger(CaseReportService.class);

    public CaseReportService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public List<IllnessCase> getReports() {
        return caseRepository.findAll();
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
            bundle.setEntry(List.of(compositionEntry));
            response =  parser.encodeResourceToString(bundle);
            LOG.info("Response: {}", response);
        }
       return response;
    }
}
