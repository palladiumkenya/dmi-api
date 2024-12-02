package com.kenyahmis.dmiapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenyahmis.dmiapi.dto.*;
import com.kenyahmis.dmiapi.model.BatchOperation;
import com.kenyahmis.dmiapi.repository.BatchOperationsRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
            LOG.info("File name is: {}", file.getOriginalFilename());
            Workbook workbook = null;
            if (file.getOriginalFilename().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (file.getOriginalFilename().endsWith("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            if (workbook != null) {
                ObjectMapper mapper = new ObjectMapper();
                int sheetCount = workbook.getNumberOfSheets();
                LOG.info("Number of sheets: {}", sheetCount);

                List<CaseDto> caseDtoList = extractCaseDtos(workbook.getSheetAt(0));
//            Map<String, Set<FlaggedConditionDto>> flaggedConditions = extractFlaggedConditions(workbook.getSheetAt(1));
                Map<String, Set<ComplaintDto>> complaints = extractComplaints(workbook.getSheetAt(1));
//            Map<String, Set<VaccinationDto>> vaccinations = extractVaccinations(workbook.getSheetAt(3));
                Map<String, Set<DiagnosisDto>> diagnosis = extractDiagnosis(workbook.getSheetAt(2));
//            Map<String, Set<RiskFactorDto>> riskFactors = extractRiskFactors(workbook.getSheetAt(5));
                Map<String, Set<VitalSignsDto>> vitalSigns = extractVitalSigns(workbook.getSheetAt(3));
                Map<String,Set<LabDto>> labs = extractLabDtos(workbook.getSheetAt(4));
                // link case to dependencies

                for (CaseDto caseDto : caseDtoList) {
//                caseDto.setFlaggedConditions((flaggedConditions.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : flaggedConditions.get(caseDto.getCaseUniqueId())));
                    caseDto.setComplaints((complaints.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : complaints.get(caseDto.getCaseUniqueId())));
//                caseDto.setVaccinations((vaccinations.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : vaccinations.get(caseDto.getCaseUniqueId())));
                caseDto.setDiagnosis((diagnosis.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : diagnosis.get(caseDto.getCaseUniqueId())));
//                caseDto.setRiskFactors((riskFactors.get(caseDto.getCaseUniqueId()) == null ? new HashSet<>() : riskFactors.get(caseDto.getCaseUniqueId())));
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

            }

        } catch (IOException e) {
            LOG.error("Failed to parse export file {}", file.getOriginalFilename(), e);
        }
    }

    private List<CaseDto> extractCaseDtos(Sheet sheet) throws IOException {
        List<CaseDto> caseDtoList = new ArrayList<>();
        LOG.info("Last case row count is: {}", sheet.getLastRowNum());
        int intialDataRow = 5;
        for (int i = intialDataRow; i <= sheet.getLastRowNum(); i++) {
//        for (int i = intialDataRow; i <= 10; i++) {
            Row row = sheet.getRow(i);
            if (getStringValue(row.getCell(0)) != null) {
                CaseDto caseDto = new CaseDto();
                SubjectDto subjectDto = new SubjectDto();
                caseDto.setCaseUniqueId(getStringValue(row.getCell(7)));
                caseDto.setHospitalIdNumber(getStringValue(row.getCell(4)));
                caseDto.setInterviewDate(getStringValue(row.getCell(8)));
                caseDto.setAdmissionDate(getStringValue(row.getCell(9)));
                caseDto.setOutpatientDate(getStringValue(row.getCell(10)));
                caseDto.setCreatedAt(getStringValue(row.getCell(11)));
                caseDto.setUpdatedAt(getStringValue(row.getCell(12)));
                caseDto.setFinalOutcome(getStringValue(row.getCell(13)));
                caseDto.setFinalOutcomeDate(getStringValue(row.getCell(10)));
                subjectDto.setPatientUniqueId(getStringValue(row.getCell(0)));
//                subjectDto.setNupi(getStringValue(row.getCell(1)));
                subjectDto.setSex(getStringValue(row.getCell(2)));
//                subjectDto.setAddress(getStringValue(row.getCell(12)));
                subjectDto.setCounty(getStringValue(row.getCell(5)));
                subjectDto.setSubCounty(getStringValue(row.getCell(6)));
                subjectDto.setDateOfBirth(getStringValue(row.getCell(3)));

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

    private Map<String, String> mapColumnValuesToMap(String columnValues) {
        Map<String, String> map = new HashMap<>();
        if (columnValues != null && !columnValues.trim().isEmpty()) {
            String[] keyValueString = columnValues.split(",");
            for (String keyValue : keyValueString) {
//                LOG.info("Key value is: {}", keyValue);
                String[] finalKeyValue = keyValue.split("\\|");
                if (finalKeyValue.length == 2) {
//                    LOG.info("Final Key value is: {}", Arrays.toString(finalKeyValue));
                    String value = finalKeyValue[1].equals("-") ? null : finalKeyValue[1].trim();
                    map.put(finalKeyValue[0].trim(), value);
                }
            }
        }
        return map;
    }

    private Map<String, Set<ComplaintDto>> extractComplaints(Sheet sheet) {
        Map<String, Set<ComplaintDto>> complaintsDtoMap = new HashMap<>();
        int initialDataRow = 5;
        for (int i = initialDataRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null) {
                String[] complaintIds = getStringValue(row.getCell(1)).split(",");
                Map<String, String> complaintNames = mapColumnValuesToMap(getStringValue(row.getCell(2)));
                Map<String, String> onsetDates = mapColumnValuesToMap(getStringValue(row.getCell(3)));
                Map<String, String> durations = mapColumnValuesToMap(getStringValue(row.getCell(4)));
                Set<ComplaintDto> complaintDtos = new HashSet<>();
                for (String complaintId : complaintIds) {
                    ComplaintDto complaintDto = new ComplaintDto();
                    complaintDto.setComplaintId(complaintId.trim());
                    String complaintName = complaintNames.get(complaintId).trim();
                    complaintDto.setComplaint(complaintName);
                    complaintDto.setOnsetDate(onsetDates.get(complaintName));
                    complaintDto.setDuration(durations.get(complaintName) == null ? null : Integer.valueOf(durations.get(complaintName)));
                    complaintDtos.add(complaintDto);
                }
                String caseUniqueId = getStringValue(row.getCell(0));
                if (caseUniqueId != null && !caseUniqueId.trim().isEmpty()) {
                    complaintsDtoMap.put(caseUniqueId, complaintDtos);
                }
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
        int intialDataRow = 5;
        for (int i = intialDataRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null) {
                String[] diagnosisIds = getStringValue(row.getCell(1)).split(",");
                Map<String, String> diagnosisNames = mapColumnValuesToMap(getStringValue(row.getCell(2)));
                Map<String, String> diagnosisDates = mapColumnValuesToMap(getStringValue(row.getCell(3)));
//                Map<String, String> diagnosisSystems = mapColumnValuesToMap(getStringValue(row.getCell(4)));
//                Map<String,String> systemCodes = mapColumnValuesToMap(getStringValue(row.getCell(5)));
                Set<DiagnosisDto> diagnosisDtos = new HashSet<>();
                for (String diagnosisId : diagnosisIds) {
                    DiagnosisDto diagnosisDto = new DiagnosisDto();
                    diagnosisDto.setDiagnosisId(diagnosisId);
                    String diagnosisName = diagnosisNames.get(diagnosisId);
                    diagnosisDto.setDiagnosis(diagnosisName);
                    diagnosisDto.setDiagnosisDate(diagnosisDates.get(diagnosisName));
                    diagnosisDto.setSystem(getStringValue(row.getCell(4)));
                    diagnosisDto.setSystemCode(getStringValue(row.getCell(5)));
                    diagnosisDtos.add(diagnosisDto);
                }
                String caseUniqueId = getStringValue(row.getCell(0));
                if (caseUniqueId != null && !caseUniqueId.trim().isEmpty()) {
                    diagnosisDtoMap.put(caseUniqueId, diagnosisDtos);
                }


//                diagnosisDtoMap.merge(getStringValue(row.getCell(0)), Set.of(diagnosisDto),
//                        (a, b) -> {
//                            Set<DiagnosisDto> newList = new HashSet<>(a);
//                            newList.addAll(b);
//                            return newList;
//                        });
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
        int intialDataRow = 5;
        for (int i = intialDataRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && getStringValue(row.getCell(0)) != null) {
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
        int initialDataRow = 5;
        for (int i = initialDataRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String labIdsCell = getStringValue(row.getCell(1));
                if (labIdsCell != null && !labIdsCell.trim().isEmpty()) {
                   String[] labIds = getStringValue(row.getCell(1)).split(",");
                    Map<String, String> labTestNames = mapColumnValuesToMap(getStringValue(row.getCell(2)));
                    Map<String, String> labTestDates = mapColumnValuesToMap(getStringValue(row.getCell(3)));
                    Map<String, String> labTestResults = mapColumnValuesToMap(getStringValue(row.getCell(4)));
                    Set<LabDto> labDtos = new HashSet<>();
                    for (String labId : labIds) {
                        LabDto labDto = new LabDto();
                        labDto.setOrderId(labId);
                        String labTestName = labTestNames.get(labId);
                        labDto.setTestName(labTestName);
                        labDto.setLabDate(labTestDates.get(labTestName));
                        labDto.setTestResult(labTestResults.get(labTestName));
                        labDtos.add(labDto);
                    }
                    String caseUniqueId = getStringValue(row.getCell(0));
                    if (caseUniqueId != null && !caseUniqueId.trim().isEmpty()) {
                        labDtoMap.put(caseUniqueId, labDtos);
                    }
                }
            }
        }
        return labDtoMap;
    }

    private String getStringValue(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = cell.getStringCellValue().isBlank() ? null: cell.getStringCellValue().trim();
                case NUMERIC -> cellValue =  Integer.toString(((int)cell.getNumericCellValue()));
            }
        }
        return cellValue;
    }

    private Double getDoubleValue(Cell cell) {
        Double cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING -> cellValue = Double.valueOf(cell.getStringCellValue().trim());
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
