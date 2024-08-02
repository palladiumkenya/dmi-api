package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.FlaggedConditionDto;
import com.kenyahmis.dmiapi.model.FlaggedCondition;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlaggedConditionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    FlaggedCondition flaggedConditionDtoToFlaggedCondition(FlaggedConditionDto flaggedConditionDto);
    @InheritConfiguration
    FlaggedConditionDto flaggedConditionToFlaggedConditionDto(FlaggedCondition flaggedCondition);
    List<FlaggedCondition> flaggedConditionDtoToFlaggedCondition(List<FlaggedConditionDto> flaggedConditionDto);
}
