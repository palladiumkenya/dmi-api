package com.kenyahmis.dmiapi.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.kenyahmis.dmiapi.dto.CaseDto;
import com.kenyahmis.dmiapi.mapper.CaseMapper;
import com.kenyahmis.dmiapi.model.IllnessCase;
import com.kenyahmis.dmiapi.model.PageData;
import com.kenyahmis.dmiapi.repository.CaseRepository;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
public class CaseReportService {
    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;
    private final Logger LOG = LoggerFactory.getLogger(CaseReportService.class);

    public CaseReportService(CaseRepository caseRepository, CaseMapper caseMapper) {
        this.caseRepository = caseRepository;
        this.caseMapper = caseMapper;
    }

    public Page<CaseDto> getReports(String startDate, String endDate, Pageable pageable) {
        Page<IllnessCase> casePage = caseRepository.findAllByLoadDateBetween(resolveStartDate(startDate),
                resolveEndDate(endDate), pageable);
        return new PageData<>(caseMapper.caseToCaseDto(casePage.getContent()), casePage.getPageable(), casePage.getTotalElements());
    }

    public String getCaseReport(UUID caseId) {
        String response = null;
        IllnessCase illnessCase = caseRepository.findById(caseId).orElse(null);
        if (illnessCase != null) {
            FhirContext ctx = FhirContext.forR4();
            IParser parser = ctx.newJsonParser().setPrettyPrint(true);
            Bundle bundle = caseMapper.caseToBundle(illnessCase);
            response = parser.encodeResourceToString(bundle);
            LOG.info("Response: {}", response);
        }
        return response;
    }

    public String getCaseReports(String startDate, String endDate, Pageable pageable) {
        Page<IllnessCase> cases = caseRepository.findAllByLoadDateBetween(resolveStartDate(startDate),
                resolveEndDate(endDate), pageable);
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newJsonParser().setPrettyPrint(true);
        Bundle bundle = caseMapper.caseListToBundle(cases.getContent());
        bundle.setTotal((int) cases.getTotalElements());
        return parser.encodeResourceToString(bundle);
    }
    private LocalDateTime resolveStartDate(String startDate) {
        LocalDateTime loadDateStart;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (startDate == null) {
            loadDateStart = LocalDate.of(2023, 1, 1).atStartOfDay();
        } else {
            loadDateStart = LocalDate.parse(startDate, formatter).atStartOfDay();
        }
        return loadDateStart;
    }
    private LocalDateTime resolveEndDate(String endDate) {
        LocalDateTime loadDateEnd;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (endDate == null) {
            loadDateEnd = LocalDateTime.now();
        } else {
            loadDateEnd = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);
        }
        return loadDateEnd;
    }
}
