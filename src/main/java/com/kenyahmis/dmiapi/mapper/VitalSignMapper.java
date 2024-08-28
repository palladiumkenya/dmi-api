package com.kenyahmis.dmiapi.mapper;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import com.kenyahmis.dmiapi.dto.VitalSignsDto;
import com.kenyahmis.dmiapi.model.VitalSign;
import com.sun.jdi.FloatType;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
abstract class VitalSignMapper {
    @Mapping(target = "vitalSignDate", source = "dto.vitalSignDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "illnessCase", ignore = true)
    abstract VitalSign vitalSignDtoToVitalSign(VitalSignsDto dto);

    @InheritConfiguration
    abstract VitalSignsDto vitalSignToVitalSignsDto(VitalSign vitalSign);

    abstract List<VitalSign> vitalSignsToVitalSigns(List<VitalSignsDto> dtos);

    List<Bundle.BundleEntryComponent> vitalSignToBundleEntryComponent(VitalSign vitalSign) {
        // vital signs (temperature, respiratory rate, oxygen saturation)
        List<Bundle.BundleEntryComponent> entries = new ArrayList<>();

        // temperature
        if (vitalSign.getTemperature() != null) {
            Bundle.BundleEntryComponent temperatureEntry = new Bundle.BundleEntryComponent();
            Observation temperatureObservation = new Observation();
            temperatureObservation.setId(vitalSign.getId().toString());
            temperatureObservation.setStatus(Observation.ObservationStatus.FINAL);
            temperatureObservation.setValue(new StringType(vitalSign.getTemperature().toString()));
            if (vitalSign.getVitalSignDate() != null) {
                temperatureObservation.setEffective(new DateTimeType(Date.valueOf(vitalSign.getVitalSignDate().toLocalDate())));
            }
            if (vitalSign.getTemperatureMode() != null) {
                CodeableConcept temperatureMode = new CodeableConcept();
                temperatureMode.setText(vitalSign.getTemperatureMode());
                temperatureObservation.setMethod(temperatureMode);
            }
            temperatureEntry.setResource(temperatureObservation);
            entries.add(temperatureEntry);
        }
        // respiratory rate
        if (vitalSign.getRespiratoryRate() != null) {
            Bundle.BundleEntryComponent respiratoryRateEntry = new Bundle.BundleEntryComponent();
            Observation respiratoryRateObservation = new Observation();
            respiratoryRateObservation.setId(vitalSign.getId().toString());
            respiratoryRateObservation.setStatus(Observation.ObservationStatus.FINAL);
            respiratoryRateObservation.setValue(new IntegerType(vitalSign.getRespiratoryRate()));
            CodeableConcept respiratoryRateConcept = new CodeableConcept();
            respiratoryRateConcept.setText("Respiratory Rate");
            respiratoryRateObservation.setCode(respiratoryRateConcept);
            respiratoryRateEntry.setResource(respiratoryRateObservation);
            entries.add(respiratoryRateEntry);
        }
        // oxygen saturation
        if (vitalSign.getOxygenSaturation() != null) {
            Bundle.BundleEntryComponent oxygenSaturationEntry = new Bundle.BundleEntryComponent();
            Observation oxygenSaturationObservation = new Observation();
            oxygenSaturationObservation.setId(vitalSign.getId().toString());
            oxygenSaturationObservation.setStatus(Observation.ObservationStatus.FINAL);
            oxygenSaturationObservation.setValue(new IntegerType(vitalSign.getOxygenSaturation()));
            if (vitalSign.getOxygenSaturationMode() != null) {
                CodeableConcept oxygenSaturationMode = new CodeableConcept();
                oxygenSaturationMode.setText(vitalSign.getOxygenSaturationMode());
                oxygenSaturationObservation.setMethod(oxygenSaturationMode);
            }
            CodeableConcept oxygenSaturationConcept = new CodeableConcept();
            oxygenSaturationConcept.setText("Oxygen Saturation");
            oxygenSaturationObservation.setCode(oxygenSaturationConcept);
            oxygenSaturationEntry.setResource(oxygenSaturationObservation);
            entries.add(oxygenSaturationEntry);
        }
        return entries;
    }
    List<Bundle.BundleEntryComponent> vitalSignToBundleEntryComponents(List<VitalSign> vitalSigns) {
        if (vitalSigns == null) {
         return null;
        }
        List<Bundle.BundleEntryComponent> entries = new ArrayList<>();
        for (VitalSign vitalSign : vitalSigns) {
            entries.addAll(vitalSignToBundleEntryComponent(vitalSign));
        }
        return entries;
    }
}
