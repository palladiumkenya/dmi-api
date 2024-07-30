package com.kenyahmis.dmiapi.mapper;

import com.kenyahmis.dmiapi.dto.ComplaintDto;
import com.kenyahmis.dmiapi.model.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    @Mapping(target ="onsetDate", source = "complaintDto.onsetDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    Complaint complaintDtoToComplaint(ComplaintDto complaintDto);
    ComplaintDto complaintToComplaintDto(Complaint complaint);
    List<Complaint> complaintDtoToComplaint(List<ComplaintDto> complaintDto);
}
