package com.shopdashboardservice.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = JsonValidator.class)
public @interface JsonValidation {

    String message() default "Field must be in JSON format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
