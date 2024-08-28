package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.FlaggedConditionDto;
import com.kenyahmis.dmiapi.model.Complaint;
import com.kenyahmis.dmiapi.model.FlaggedCondition;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
abstract class FlaggedConditionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract FlaggedCondition flaggedConditionDtoToFlaggedCondition(FlaggedConditionDto flaggedConditionDto);
    @InheritConfiguration
    abstract FlaggedConditionDto flaggedConditionToFlaggedConditionDto(FlaggedCondition flaggedCondition);
    abstract List<FlaggedCondition> flaggedConditionDtoToFlaggedCondition(List<FlaggedConditionDto> flaggedConditionDto);
    Bundle.BundleEntryComponent flaggedConditionToBundleEntryComponent(FlaggedCondition flaggedCondition) {
        Bundle.BundleEntryComponent flaggedConditionEntry = new Bundle.BundleEntryComponent();
        Condition condition = new Condition();
        condition.setId(flaggedCondition.getId().toString());
        CodeableConcept conditionConcept = new CodeableConcept();
        Coding conditionCoding = new Coding();
        conditionCoding.setDisplay(flaggedCondition.getConditionName());
        conditionConcept.setCoding(List.of(conditionCoding));
        condition.setCode(conditionConcept);
        flaggedConditionEntry.setResource(condition);
        return flaggedConditionEntry;
    }
    abstract List<Bundle.BundleEntryComponent> flaggedConditionToBundleEntryComponent(List<FlaggedCondition> flaggedCondition);
}
