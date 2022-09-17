package com.sergey.zhuravlev.social.configuration.message;

import com.sergey.zhuravlev.social.exception.SocialServiceException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ErrorMessageResolver {

    private final ResourceBundleMessageSource errors;

    public ErrorMessageResolver() {
        errors = new ResourceBundleMessageSource();
        errors.setBasename("errors");
    }

    public String getMessage(SocialServiceException exception, Locale locale) {
        return errors.getMessage(exception.getCode().name(), new Object[]{}, locale);
    }

}
