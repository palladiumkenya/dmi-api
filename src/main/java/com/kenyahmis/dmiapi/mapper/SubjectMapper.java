package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.SubjectDto;
import com.kenyahmis.dmiapi.model.Subject;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target="dateOfBirth", source = "dto.dateOfBirth",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    Subject subjectDtoToSubject(SubjectDto dto);
    @InheritConfiguration
    SubjectDto subjectToSubjectDto(Subject subject);
}
