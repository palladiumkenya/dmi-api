package com.kenyahmis.dmiapi.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidFlaggedConditionValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ValidFlaggedCondition {
    String message() default "Invalid condition value. The accepted conditions are: DYSENTERY, CHOLERA, ILI, SARI," +
            " RIFT VALLEY FEVER, MALARIA, CHIKUNGUNYA, POLIOMYELITIS, VIRAL HAEMORRHAGIC FEVER, MEASLES Acceptable syndromes are: " +
            "ACUTE JAUNDICE SYNDROME, ACUTE MENINGITIS AND ENCEPHALITIS  SYNDROME, ACUTE FLACCID PARALYSIS," +
            " SEVERE ACUTE RESPIRATORY INFECTION, ACUTE HAEMORRHAGIC FEVER, ACUTE WATERY DIARRHOEAL, NEUROLOGICAL  SYNDROME," +
            " ACUTE FEBRILE RASH INFECTIONS, ACUTE FEBRILE ILLNESS, ACUTE HAEMORRHAGIC FEVER";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
