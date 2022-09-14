package com.sergey.zhuravlev.social.validation;

import com.sergey.zhuravlev.social.contrains.PatternConstrains;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, String> {

    @Override
    public void initialize(EmailOrPhone emailOrPhone) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches(PatternConstrains.EMAIL_OR_PHONE_PATTERN_VALUE) && (contactField.length() >= 7) && (contactField.length() <= 191);
    }

}
