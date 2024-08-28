package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.ComplaintDto;
import com.kenyahmis.dmiapi.model.Complaint;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ComplaintMapper {
    @Mapping(target ="onsetDate", source = "complaintDto.onsetDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    public abstract Complaint complaintDtoToComplaint(ComplaintDto complaintDto);
    @InheritConfiguration
    public abstract ComplaintDto complaintToComplaintDto(Complaint complaint);

    Bundle.BundleEntryComponent complaintToBundleEntryComponent(Complaint complaint) {
        Bundle.BundleEntryComponent entry = new Bundle.BundleEntryComponent();
        Reference subjectReference = new Reference();
        subjectReference.setDisplay("Case Patient");
        subjectReference.setReference("Patient/"+ complaint.getIllnessCase().getSubject().getId().toString());

        Observation complaintObservation = new Observation();
        complaintObservation.setId(complaint.getId().toString());
        complaintObservation.setStatus(Observation.ObservationStatus.FINAL);
        complaintObservation.setSubject(subjectReference);

        CodeableConcept complaintConcept = new CodeableConcept();
        Coding complaintCoding = new Coding();
        complaintCoding.setSystem("http://staging.org/complaint");
        complaintCoding.setDisplay(complaint.getComplaint());
        complaintConcept.setCoding(List.of(complaintCoding));
        complaintObservation.setCode(complaintConcept);
        entry.setResource(complaintObservation);
        return entry;
    }
    abstract List<Bundle.BundleEntryComponent> complaintToBundleEntryComponent(List<Complaint> complaints);
    abstract List<Complaint> complaintDtoToComplaint(List<ComplaintDto> complaintDto);
}
