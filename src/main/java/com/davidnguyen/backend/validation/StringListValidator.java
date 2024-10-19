package com.davidnguyen.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StringListValidator implements ConstraintValidator<AllStringAndNotBlank, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            log.info("Validation passed: List is null or empty");
            return true;
        }

        for (String obj : value) {
            if (obj == null) {
                log.warn("Validation failed: List contains null element");
                return false;
            }
            if (obj.trim().isEmpty()) {
                log.warn("Validation failed: List contains blank element");
                return false;
            }
        }
        log.info("Validation passed: All elements are non-blank strings");
        return true;
    }
}