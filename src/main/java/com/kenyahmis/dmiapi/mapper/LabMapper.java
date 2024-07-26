package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.LabDto;
import com.kenyahmis.dmiapi.model.Lab;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabMapper {
    @Mapping(target ="labDate", source = "labDto.labDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    Lab labDtoToLab(LabDto labDto);
    LabDto labToLabDto(Lab lab);
    List<Lab> labDtoToLab(List<LabDto> labDto);
}
