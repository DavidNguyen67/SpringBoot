package com.davidnguyen.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringListValidator implements ConstraintValidator<AllStringElement, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;

        for (Object obj : value) {
            if (!(obj instanceof String)) {
                return false;
            }
        }
        return true;
    }
}
