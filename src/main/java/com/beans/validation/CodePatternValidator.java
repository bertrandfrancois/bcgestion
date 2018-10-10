package com.beans.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodePatternValidator implements ConstraintValidator<ValidCode, String> {

    @Override
    public void initialize(ValidCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^([0-9]{7})$");
    }
}
