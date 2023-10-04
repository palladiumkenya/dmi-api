package com.kenyahmis.dmiapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class IllnessCaseDto {

    @NotBlank
    private String patientUniqueId;
    private String nupi;
    @NotBlank
    private String caseUniqueId;
    @NotBlank
    private String hospitalIdNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime interviewDate;
    private LocalDate dateOfBirth;
    private Integer ageInMonths;
    private Integer ageInYears;
    @NotBlank
    private String sex;
    private String address;
    private Double temperature;
    private Boolean voided;
    @Valid
    private List<ComplaintDto> complaintDtoList = new ArrayList<>();
    @Valid
    private List<LabDto> labDtoList = new ArrayList<>();
    @Valid
    private List<DiagnosisDto> diagnosis = new ArrayList<>();
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    @NotBlank
    private String county;
    @NotBlank
    private String subCounty;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    public IllnessCaseDto() {}
}
