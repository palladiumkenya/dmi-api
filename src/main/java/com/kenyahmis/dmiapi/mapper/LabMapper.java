package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.LabDto;
import com.kenyahmis.dmiapi.model.Lab;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class LabMapper {
    @Mapping(target ="labDate", source = "labDto.labDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract Lab labDtoToLab(LabDto labDto);
    @InheritConfiguration
    abstract LabDto labToLabDto(Lab lab);
    abstract List<Lab> labDtoToLab(List<LabDto> labDto);
    Bundle.BundleEntryComponent labToBundleEntryComponent(Lab lab) {
        Bundle.BundleEntryComponent labResultEntry = new Bundle.BundleEntryComponent();
        Reference subjectReference = new Reference();
        subjectReference.setDisplay("Case Patient");
        subjectReference.setReference("Patient/"+ lab.getIllnessCase().getSubject().getId().toString());
        Observation labResult = new Observation();
        labResult.setId(lab.getId().toString());
        labResult.setStatus(Observation.ObservationStatus.FINAL);
        labResult.setSubject(subjectReference);
        labResult.setValue(new StringType(lab.getTestResult()));
        CodeableConcept labCode = new CodeableConcept();
        Coding labCodeCoding = new Coding();
        labCodeCoding.setSystem("http://staging.org/lab");
        labCodeCoding.setDisplay(lab.getTestResult());
        labCode.setCoding(List.of(labCodeCoding));
        labResult.setCode(labCode);
        labResultEntry.setResource(labResult);
        return labResultEntry;
    }
    abstract List<Bundle.BundleEntryComponent> labToBundleEntryComponent(List<Lab> lab);
}
