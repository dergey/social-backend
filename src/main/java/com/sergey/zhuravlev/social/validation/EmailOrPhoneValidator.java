package com.sergey.zhuravlev.social.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, String> {

    @Override
    public void initialize(EmailOrPhone emailOrPhone) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("\\d{7,15}|\\w+@\\w+\\.\\w{2,3}") && (contactField.length() >= 7) && (contactField.length() <= 191);
    }

}
