package com.sergey.zhuravlev.social.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfiguration {

    @Bean("mailSubjectMessageSource")
    public ResourceBundleMessageSource mailSubjectMessageSource() {
        ResourceBundleMessageSource mailSubject = new ResourceBundleMessageSource();
        mailSubject.setBasename("mail-subjects");
        return mailSubject;
    }

}
