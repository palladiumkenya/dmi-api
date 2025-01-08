package com.kenyahmis.dmiapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidFlaggedConditionValidator implements ConstraintValidator<ValidFlaggedCondition, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        List<String> conditionList = List.of("DYSENTERY", "CHOLERA","ILI", "SARI", "RIFT VALLEY FEVER", "MALARIA",
                "CHIKUNGUNYA", "POLIOMYELITIS", "VIRAL HAEMORRHAGIC FEVER", "MEASLES", "ACUTE JAUNDICE SYNDROME", "ACUTE MENINGITIS AND ENCEPHALITIS SYNDROME", "ACUTE FLACCID PARALYSIS",
                "SEVERE ACUTE RESPIRATORY INFECTION", "ACUTE HAEMORRHAGIC FEVER", "ACUTE WATERY DIARRHOEAL", "NEUROLOGICAL  SYNDROME",
                "ACUTE FEBRILE RASH INFECTIONS", "ACUTE FEBRILE ILLNESS", "ACUTE HAEMORRHAGIC FEVER", "MPOX", "INFLUENZA LIKE ILLNESS",
                "SEVERE ACUTE RESPIRATORY INFECTION", "HIV");
        return conditionList.contains(value.toUpperCase());
    }
}
