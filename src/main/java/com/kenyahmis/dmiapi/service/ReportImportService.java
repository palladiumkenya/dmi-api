package com.kenyahmis.dmiapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenyahmis.dmiapi.dto.*;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportImportService {
    private final Logger LOG = LoggerFactory.getLogger(ReportImportService.class);
    private final Validator validator;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BatchOperationsRepository batchOperationsRepository;

    public ReportImportService(Validator validator, KafkaTemplate<String, Object> kafkaTemplate,
                               BatchOperationsRepository batchOperationsRepository) {
        this.validator = validator;
        this.kafkaTemplate = kafkaTemplate;
        this.batchOperationsRepository = batchOperationsRepository;
    }

    public void parseExport(final MultipartFile file, final String emrName) {

        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            ObjectMapper mapper = new ObjectMapper();
            int sheetCount = workbook.getNumberOfSheets();
            LOG.info("Number of sheets: {}", sheetCount);

            List<CaseDto> caseDtoList = extractCaseDtos(workbook.getSheetAt(0));
            Map<String, Set<FlaggedConditionDto>> flaggedConditions = extractFlaggedConditions(workbook.getSheetAt(1));
            Map<String, Set<ComplaintDto>> complaints = extractComplaints(workbook.getSheetAt(2));
            Map<String, Set<VaccinationDto>> vaccinations = extractVaccinations(workbook.getSheetAt(3));
            Map<String, Set<DiagnosisDto>> diagnosis = extractDiagnosis(workbook.getSheetAt(4));
            Map<String, Set<RiskFactorDto>> riskFactors = extractRiskFactors(workbook.getSheetAt(5));
            Map<String, Set<VitalSignsDto>> vitalSigns = extractVitalSigns(workbook.getSheetAt(6));
            Map<String,Set<LabDto>> labs = extractLabDtos(workbook.getSheetAt(7));
            // link case to dependencies

            for (CaseDto caseDto : caseDtoList) {
                caseDto.setFlaggedConditions((flaggedConditions.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : flaggedConditions.get(caseDto.getCaseUniqueId())));
                caseDto.setComplaints((complaints.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : complaints.get(caseDto.getCaseUniqueId())));
                caseDto.setVaccinations((vaccinations.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : vaccinations.get(caseDto.getCaseUniqueId())));
                caseDto.setDiagnosis((diagnosis.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : diagnosis.get(caseDto.getCaseUniqueId())));
                caseDto.setRiskFactors((riskFactors.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : riskFactors.get(caseDto.getCaseUniqueId())));
                caseDto.setVitalSigns((vitalSigns.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : vitalSigns.get(caseDto.getCaseUniqueId())));
                caseDto.setLabs((labs.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : labs.get(caseDto.getCaseUniqueId())));
                Set<ConstraintViolation<CaseDto>> violations = validator.validate(caseDto);
                if (!violations.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<CaseDto> violation : violations) {
                        sb.append(violation.getPropertyPath().toString())
                                .append(" ")
                                .append(violation.getMessage()).append("\n");
                    }
                    LOG.error("Validation failed for case {}: {} " ,caseDto.getCaseUniqueId(), sb);
                }
            }
            LOG.info("Cases dtos {}", mapper.writeValueAsString(caseDtoList));
            // submit to kafka topic
            BatchOperation batch = new BatchOperation(caseDtoList.size(), "INCOMPLETE",
                    null, LocalDateTime.now());
            batchOperationsRepository.save(batch);
            caseDtoList.forEach(iCase -> kafkaTemplate.send("visitTopic", new CaseMessageDto(batch.getId(), iCase,  emrName)));
        } catch (IOException e) {
            LOG.error("Failed to parse export file {}", file.getOriginalFilename(), e);
        }
    }

    private List<CaseDto> extractCaseDtos(Sheet sheet) throws IOException {
        List<CaseDto> caseDtoList = new ArrayList<>();
        LOG.info("Last case row count is: {}", sheet.getLastRowNum());
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (getStringValue(row.getCell(0)) != null) {
                CaseDto caseDto = new CaseDto();
                SubjectDto subjectDto = new SubjectDto();
                caseDto.setCaseUniqueId(getStringValue(row.getCell(0)));
                caseDto.setHospitalIdNumber(getStringValue(row.getCell(1)));
                caseDto.setInterviewDate(getStringValue(row.getCell(2)));
                caseDto.setAdmissionDate(getStringValue(row.getCell(3)));
                caseDto.setOutpatientDate(getStringValue(row.getCell(4)));
                caseDto.setCreatedAt(getStringValue(row.getCell(5)));
                caseDto.setUpdatedAt(getStringValue(row.getCell(6)));
                caseDto.setFinalOutcome(getStringValue(row.getCell(7)));
                caseDto.setFinalOutcomeDate(getStringValue(row.getCell(8)));
                subjectDto.setPatientUniqueId(getStringValue(row.getCell(9)));
                subjectDto.setNupi(getStringValue(row.getCell(10)));
                subjectDto.setSex(getStringValue(row.getCell(11)));
                subjectDto.setAddress(getStringValue(row.getCell(12)));
                subjectDto.setCounty(getStringValue(row.getCell(13)));
                subjectDto.setSubCounty(getStringValue(row.getCell(14)));
                subjectDto.setDateOfBirth(getStringValue(row.getCell(15)));

                caseDto.setSubject(subjectDto);
                caseDtoList.add(caseDto);
            }
        }

        return caseDtoList;
    }

    private Map<String, Set<FlaggedConditionDto>> extractFlaggedConditions(Sheet sheet) {
        Map<String, Set<FlaggedConditionDto>> flaggedConditionsMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                FlaggedConditionDto flaggedConditionDto = new FlaggedConditionDto();
                flaggedConditionDto.setConditionId(getStringValue(row.getCell(1)));
                flaggedConditionDto.setConditionName(getStringValue(row.getCell(2)));
                flaggedConditionsMap.merge(getStringValue(row.getCell(0)), Set.of(flaggedConditionDto),
                        (a, b) -> {
                            Set<FlaggedConditionDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return flaggedConditionsMap;
    }

    private Map<String, Set<ComplaintDto>> extractComplaints(Sheet sheet) {
        Map<String, Set<ComplaintDto>> complaintsDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null) {
                ComplaintDto complaintDto = new ComplaintDto();
                complaintDto.setComplaintId(getStringValue(row.getCell(1)));
                complaintDto.setComplaint(getStringValue(row.getCell(2)));
                complaintDto.setOnsetDate(getStringValue(row.getCell(3)));
                complaintDto.setDuration(getIntValue(row.getCell(4)));
                complaintsDtoMap.merge(getStringValue(row.getCell(0)), Set.of(complaintDto),
                        (a, b) -> {
                            Set<ComplaintDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return complaintsDtoMap;
    }

    private Map<String, Set<VaccinationDto>> extractVaccinations(Sheet sheet) {
        Map<String, Set<VaccinationDto>> vaccinationsDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                VaccinationDto vaccinationDto = new VaccinationDto();
                vaccinationDto.setVaccinationId(getStringValue(row.getCell(1)));
                vaccinationDto.setVaccination(getStringValue(row.getCell(2)));
                vaccinationDto.setDoses(getIntValue(row.getCell(3)));
                vaccinationDto.setVerified(getBooleanValue(row.getCell(4)));
                vaccinationsDtoMap.merge(getStringValue(row.getCell(0)), Set.of(vaccinationDto),
                        (a, b) -> {
                            Set<VaccinationDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }

        }
        return vaccinationsDtoMap;
    }

    private Map<String, Set<DiagnosisDto>> extractDiagnosis(Sheet sheet) {
        Map<String, Set<DiagnosisDto>> diagnosisDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null) {
                DiagnosisDto diagnosisDto = new DiagnosisDto();
                diagnosisDto.setDiagnosisId(getStringValue(row.getCell(1)));
                diagnosisDto.setDiagnosis(getStringValue(row.getCell(2)));
                diagnosisDto.setDiagnosisDate(getStringValue(row.getCell(3)));
                diagnosisDto.setSystem(getStringValue(row.getCell(4)));
                diagnosisDto.setSystemCode(getStringValue(row.getCell(5)));
                diagnosisDtoMap.merge(getStringValue(row.getCell(0)), Set.of(diagnosisDto),
                        (a, b) -> {
                            Set<DiagnosisDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return diagnosisDtoMap;
    }

    private Map<String, Set<RiskFactorDto>> extractRiskFactors(Sheet sheet) {
        Map<String, Set<RiskFactorDto>> riskFactorDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                RiskFactorDto riskFactorDto = new RiskFactorDto();
                riskFactorDto.setCondition(getStringValue(row.getCell(1)));
                riskFactorDto.setRiskFactorId(getStringValue(row.getCell(2)));
                riskFactorDtoMap.merge(getStringValue(row.getCell(0)), Set.of(riskFactorDto),
                        (a, b) -> {
                            Set<RiskFactorDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return riskFactorDtoMap;
    }

    private Map<String, Set<VitalSignsDto>> extractVitalSigns(Sheet sheet) {
        Map<String, Set<VitalSignsDto>> vitalSignDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                VitalSignsDto vitalSignsDto = new VitalSignsDto();
                vitalSignsDto.setTemperature(getDoubleValue(row.getCell(1)));
                vitalSignsDto.setTemperatureMode(getStringValue(row.getCell(2)));
                vitalSignsDto.setRespiratoryRate(getIntValue(row.getCell(3)));
                vitalSignsDto.setOxygenSaturation(getIntValue(row.getCell(4)));
                vitalSignsDto.setOxygenSaturationMode(getStringValue(row.getCell(5)));
                vitalSignsDto.setVitalSignId(getStringValue(row.getCell(6)));
                vitalSignsDto.setVitalSignDate(getStringValue(row.getCell(7)));
                vitalSignDtoMap.merge(getStringValue(row.getCell(0)), Set.of(vitalSignsDto),
                        (a, b) -> {
                            Set<VitalSignsDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return vitalSignDtoMap;
    }

    private Map<String, Set<LabDto>> extractLabDtos(Sheet sheet) {
        Map<String, Set<LabDto>> labDtoMap = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                LabDto labDto = new LabDto();
                labDto.setOrderId(getStringValue(row.getCell(1)));
                labDto.setTestResult(getStringValue(row.getCell(2)));
                labDto.setLabDate(getStringValue(row.getCell(3)));
                labDto.setTestName(getStringValue(row.getCell(4)));
                labDto.setUnit(getStringValue(row.getCell(5)));
                labDto.setUpperLimit(getStringValue(row.getCell(6)));
                labDto.setLowerLimit(getStringValue(row.getCell(7)));
                labDtoMap.merge(getStringValue(row.getCell(0)), Set.of(labDto),
                        (a, b) -> {
                            Set<LabDto> newList = new HashSet<>(a);
                            newList.addAll(b);
                            return newList;
                        });
            }
        }
        return labDtoMap;
    }

    private String getStringValue(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = cell.getStringCellValue();
                case NUMERIC -> cellValue =  Integer.toString(((int)cell.getNumericCellValue()));
            }
        }
        return cellValue;
    }

    private Double getDoubleValue(Cell cell) {
        Double cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = Double.valueOf(cell.getStringCellValue());
                case NUMERIC -> cellValue = cell.getNumericCellValue();
            }
        }
        return cellValue;
    }

    private Integer getIntValue(Cell cell) {
        Integer cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = Integer.valueOf(cell.getStringCellValue()) ;
                case NUMERIC -> cellValue =  (int)cell.getNumericCellValue();
            }
        }
        return cellValue;
    }
    private Boolean getBooleanValue(Cell cell) {
        Boolean cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = Boolean.valueOf(cell.getStringCellValue()) ;
                case BOOLEAN -> cellValue =  cell.getBooleanCellValue();
            }
        }
        return cellValue;
    }
}
