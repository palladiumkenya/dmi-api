package com.kenyahmis.dmiapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kenyahmis.dmiapi.validator.ValidTimestamp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubjectDto {
    @NotBlank
    private String patientUniqueId;
    private String nupi;
    @NotBlank
    private String sex;
    private String address;
    @ValidTimestamp
    private String dateOfBirth;
    @NotBlank
    private String county;
    @NotBlank
    private String subCounty;
}

