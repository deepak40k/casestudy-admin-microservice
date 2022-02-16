package com.casestudy.skilltracker.admin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssociateIDValidator implements ConstraintValidator<AssociateID, String> {

    @Override
    public boolean isValid(String associateID, ConstraintValidatorContext constraintValidatorContext) {
        return associateID.startsWith("CTS");
    }
}
