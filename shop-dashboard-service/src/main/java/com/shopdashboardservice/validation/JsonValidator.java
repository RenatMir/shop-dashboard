package com.shopdashboardservice.validation;

import com.shopdashboardservice.utils.CommonUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JsonValidator implements ConstraintValidator<JsonValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CommonUtils.isJSONValid(value);
    }
}
