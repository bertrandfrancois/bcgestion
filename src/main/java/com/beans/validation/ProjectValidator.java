package com.beans.validation;

import com.beans.Project;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectValidator implements ConstraintValidator<ValidProject, Project> {

    @Override
    public void initialize(ValidProject constraintAnnotation) {

    }

    @Override
    public boolean isValid(Project value, ConstraintValidatorContext context) {
        return value.getStartDate().before(value.getEndDate());
    }
}
