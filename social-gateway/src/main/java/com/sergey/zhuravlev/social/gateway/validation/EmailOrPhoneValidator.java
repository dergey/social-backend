package com.sergey.zhuravlev.social.gateway.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, String> {

    @Override
    public void initialize(EmailOrPhone emailOrPhone) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return contactField != null && (isPhone(contactField) || isEmail(contactField)) && (contactField.length() >= 7) && (contactField.length() <= 191);
    }

    public static boolean isPhone(String contactField) {
        return contactField.matches("\\d{7,15}");
    }

    public static boolean isEmail(String contactField) {
        return contactField.matches("\\w+@\\w+\\.\\w{2,3}");
    }

}
