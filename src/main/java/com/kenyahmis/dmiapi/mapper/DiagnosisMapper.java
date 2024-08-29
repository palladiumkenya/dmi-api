package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.DiagnosisDto;
import com.kenyahmis.dmiapi.model.Diagnosis;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DiagnosisMapper {

    @Mapping(target ="diagnosisDate", source = "diagnosisDto.diagnosisDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract Diagnosis diagnosisDtoToDiagnosis(DiagnosisDto diagnosisDto);
    @InheritConfiguration
    abstract DiagnosisDto diagnosisToDiagnosisDto(Diagnosis diagnosis);
    abstract List<Diagnosis> diagnosisDtoToDiagnosis(List<DiagnosisDto> diagnosisDto);
    Bundle.BundleEntryComponent diagnosisToBundleEntryComponent(Diagnosis diagnosis) {
        Bundle.BundleEntryComponent diagnosisEntry = new Bundle.BundleEntryComponent();
        Reference subjectReference = new Reference();
        subjectReference.setDisplay("Case Patient");
        subjectReference.setReference("Patient/"+ diagnosis.getIllnessCase().getSubject().getId().toString());
        Observation diagnosisObservation = new Observation();
        diagnosisObservation.setId(diagnosis.getId().toString());
        diagnosisObservation.setStatus(Observation.ObservationStatus.FINAL);
        diagnosisObservation.setSubject(subjectReference);
        diagnosisObservation.setValue(new StringType(diagnosis.getDiagnosis()));
        CodeableConcept diagnosisConcept = new CodeableConcept();
        Coding diagnosisCoding = new Coding();
        diagnosisCoding.setSystem("http://staging.org/diagnosis");
        diagnosisCoding.setDisplay(diagnosis.getDiagnosis());
        diagnosisConcept.setCoding(List.of(diagnosisCoding));
        diagnosisObservation.setCode(diagnosisConcept);
//          diagnosis.setCategory();
        diagnosisEntry.setResource(diagnosisObservation);
        return diagnosisEntry;
    }

    abstract List<Bundle.BundleEntryComponent> diagnosisToBundleEntryComponent(List<Diagnosis> diagnosisList);

}
