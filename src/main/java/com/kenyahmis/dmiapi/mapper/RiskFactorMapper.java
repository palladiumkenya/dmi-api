package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.RiskFactorDto;
import com.kenyahmis.dmiapi.model.RiskFactor;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RiskFactorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    RiskFactor riskFactorDtoToRiskFactor(RiskFactorDto riskFactorDto);
    @InheritConfiguration
    RiskFactorDto riskFactorToRiskFactorDto(RiskFactor riskFactor);
    List<RiskFactor> riskFactorDtoToRiskFactor(List<RiskFactorDto> riskFactorDto);
}
