package com.kenyahmis.dmiapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SubjectSummary {
    private UUID subjectId;
    private String patientUniqueId;
    private String mflCode;
}
