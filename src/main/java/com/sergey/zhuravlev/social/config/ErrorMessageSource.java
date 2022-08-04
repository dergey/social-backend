package com.sergey.zhuravlev.social.config;

import com.sergey.zhuravlev.social.exception.SocialServiceException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ErrorMessageSource {

    private final ResourceBundleMessageSource errors;

    public ErrorMessageSource() {
        errors = new ResourceBundleMessageSource();
        errors.setBasename("errors");
    }

    public String getMessage(SocialServiceException exception, Locale locale) {
        return errors.getMessage(exception.getCode().name(), new Object[]{}, locale);
    }

//    public String getMessage(SocialServiceFieldException exception, Locale locale) {
//        return errors.getMessage(exception.getCode().name(), new Object[]{}, locale);
//    }

}
