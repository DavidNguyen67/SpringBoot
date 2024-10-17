package com.davidnguyen.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringListValidator.class)
public @interface AllStringAndNotBlank {
    String message() default "All elements must be strings";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
