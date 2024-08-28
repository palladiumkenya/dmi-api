package com.kenyahmis.dmiapi.mapper;

import ca.uhn.fhir.rest.param.InternalCodingDt;
import com.kenyahmis.dmiapi.dto.VaccinationDto;
import com.kenyahmis.dmiapi.model.Vaccination;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class VaccinationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract Vaccination vaccinationDtoToVaccination(VaccinationDto dto);
    @InheritConfiguration
    abstract VaccinationDto vaccinationToVaccinationDto(Vaccination entity);
    abstract List<Vaccination> vaccinationDtoToVaccination(List<VaccinationDto> dto);
    Bundle.BundleEntryComponent vaccinationToBundleEntryComponent(Vaccination vaccination) {
        Immunization immunization = new Immunization();
        Reference subjectReference = new Reference();
        subjectReference.setDisplay("Case Patient");
        subjectReference.setReference("Patient/"+ vaccination.getIllnessCase().getSubject().getId().toString());
        immunization.setId(vaccination.getId().toString());
        immunization.setStatus(Immunization.ImmunizationStatus.COMPLETED);
        if (vaccination.getDoses() != null) {
            immunization.setDoseQuantity(new Quantity(vaccination.getDoses()));
        }
        immunization.setPatient(subjectReference);
        // vaccine code
        CodeableConcept vaccineCodeConcept = new CodeableConcept();
        Coding vaccineCode = new Coding();
//        vaccineCode.setSystem("http://hl7.org/fhir/sid/cvx");
//        vaccineCode.setCode("56");
        vaccineCode.setDisplay(vaccination.getVaccination());
        vaccineCodeConcept.setCoding(List.of(vaccineCode));
        vaccineCodeConcept.setText(vaccination.getVaccination());
        immunization.setVaccineCode(vaccineCodeConcept);

        Bundle.BundleEntryComponent immunizationEntry = new Bundle.BundleEntryComponent();
        immunizationEntry.setResource(immunization);
        return immunizationEntry;
    }

    abstract List<Bundle.BundleEntryComponent> vaccinationToBundleEntryComponents(List<Vaccination> vaccinations);

}
