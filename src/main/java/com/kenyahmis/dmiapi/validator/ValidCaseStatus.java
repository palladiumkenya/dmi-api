package com.kenyahmis.dmiapi.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidCaseStatusValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ValidCaseStatus {
    String message() default "Invalid case status. The status can be either of preliminary | final | amended | entered-in-error";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
