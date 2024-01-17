package com.kenyahmis.dmiapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidCaseStatusValidator implements ConstraintValidator<ValidCaseStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> statusList = List.of("preliminary","final","amended" ,"entered-in-error");
        return statusList.contains(value);
    }
}
