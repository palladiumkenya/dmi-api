package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.RiskFactorDto;
import com.kenyahmis.dmiapi.model.RiskFactor;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
abstract class RiskFactorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract RiskFactor riskFactorDtoToRiskFactor(RiskFactorDto riskFactorDto);
    @InheritConfiguration
    abstract RiskFactorDto riskFactorToRiskFactorDto(RiskFactor riskFactor);
    abstract List<RiskFactor> riskFactorDtoToRiskFactor(List<RiskFactorDto> riskFactorDto);
    Bundle.BundleEntryComponent riskFactorToBundleEntryComponent(RiskFactor riskFactor) {
        Bundle.BundleEntryComponent riskFactorEntry = new Bundle.BundleEntryComponent();
        Condition riskFactorCondition = new Condition();
        riskFactorCondition.setId(riskFactor.getId().toString());
        CodeableConcept riskFactorConcept = new CodeableConcept();
        Coding riskFactorCoding = new Coding();
        riskFactorCoding.setSystem("http://staging.org/riskfactor");
        riskFactorCoding.setDisplay("Diabetes");
        riskFactorConcept.setCoding(List.of(riskFactorCoding));
        riskFactorCondition.setCode((riskFactorConcept));
        riskFactorEntry.setResource(riskFactorCondition);
        return riskFactorEntry;
    }
    abstract List<Bundle.BundleEntryComponent> riskFactorToBundleEntryComponent(List<RiskFactor> riskFactor);
}
