package com.davidnguyen.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringListValidator implements ConstraintValidator<AllStringAndNotBlank, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;

        for (String obj : value) {
            if (obj == null) {
                return false;
            }
            if (obj.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
