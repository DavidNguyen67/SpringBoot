package com.davidnguyen.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringListValidator implements ConstraintValidator<ValidStringList, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        for (Object obj : value) {
            if (!(obj instanceof String)) {
                return false;
            }
        }
        return true;
    }
}
