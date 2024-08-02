package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.VitalSignsDto;
import com.kenyahmis.dmiapi.model.VitalSign;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VitalSignMapper {
    @Mapping(target = "vitalSignDate", source = "dto.vitalSignDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    VitalSign vitalSignDtoToVitalSign(VitalSignsDto dto);
    @InheritConfiguration
    VitalSignsDto vitalSignToVitalSignsDto(VitalSign vitalSign);
    List<VitalSign> vitalSignsToVitalSigns(List<VitalSignsDto> dtos);
}
