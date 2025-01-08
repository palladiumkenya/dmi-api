package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.ARTLinkageDto;
import com.kenyahmis.dmiapi.model.ARTLinkage;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ARTLinkageMapper {
    @Mapping(target ="artStartDate", source = "artLinkageDto.artStartDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    ARTLinkage artLinkageDtoArtLinkage(ARTLinkageDto artLinkageDto);
    @InheritConfiguration
    ARTLinkageDto artLinkageToArtLinkageDto(ARTLinkage artLinkage);
    List<ARTLinkage> artLinkageDtoToArtLinkage(List<ARTLinkageDto> artLinkageDtoList);
}
