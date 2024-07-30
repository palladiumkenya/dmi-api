package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.VaccinationDto;
import com.kenyahmis.dmiapi.model.Vaccination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    Vaccination vaccinationDtoToVaccination(VaccinationDto dto);
    VaccinationDto vaccinationToVaccinationDto(Vaccination entity);
    List<Vaccination> vaccinationDtoToVaccination(List<VaccinationDto> dto);
}
