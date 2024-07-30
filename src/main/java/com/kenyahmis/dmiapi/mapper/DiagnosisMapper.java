package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.DiagnosisDto;
import com.kenyahmis.dmiapi.model.Diagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper {

    @Mapping(target ="diagnosisDate", source = "diagnosisDto.diagnosisDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    Diagnosis diagnosisDtoToDiagnosis(DiagnosisDto diagnosisDto);
    DiagnosisDto diagnosisToDiagnosisDto(Diagnosis diagnosis);
    List<Diagnosis> diagnosisDtoToDiagnosis(List<DiagnosisDto> diagnosisDto);
}
