package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.SubjectDto;
import com.kenyahmis.dmiapi.model.Subject;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;
import java.util.List;

@Mapper(componentModel = "spring")
abstract class SubjectMapper {
    @Mapping(target="dateOfBirth", source = "dto.dateOfBirth",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    abstract Subject subjectDtoToSubject(SubjectDto dto);
    @InheritConfiguration
    abstract SubjectDto subjectToSubjectDto(Subject subject);
    Bundle.BundleEntryComponent subjectToBundleEntryComponent(Subject subject) {
        Patient patient = new Patient();
        patient.setId(subject.getId().toString());
        patient.setBirthDate(Date.valueOf(subject.getDateOfBirth().toLocalDate()));
        switch (subject.getSex()) {
            case "FEMALE":
                patient.setGender(Enumerations.AdministrativeGender.FEMALE);
                break;
            case "MALE":
                patient.setGender(Enumerations.AdministrativeGender.MALE);
                break;
            case "OTHER":
                patient.setGender(Enumerations.AdministrativeGender.OTHER);
                break;
        }
        // nupi & patient facility id
        Identifier nupi = new Identifier();
        nupi.setSystem("http://staging.org/nupi");
        nupi.setValue(subject.getNupi());
        patient.setIdentifier(List.of(nupi));

        Address address = new Address();
        address.setLine(List.of(new StringType(subject.getAddress())));
        Extension countyExtension = new Extension();
        countyExtension.setUrl("http://staging.org/county");
        countyExtension.setValue(new StringType(subject.getCounty()));

        Extension subCountyExtension = new Extension();
        subCountyExtension.setUrl("http://staging.org/subcounty");
        subCountyExtension.setValue(new StringType(subject.getSubCounty()));
        address.setExtension(List.of(countyExtension, subCountyExtension));
        patient.setAddress(List.of(address));
        Bundle.BundleEntryComponent patientEntry = new Bundle.BundleEntryComponent();
        patientEntry.setResource(patient);
        return patientEntry;
    }
}
