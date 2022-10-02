package com.sergey.zhuravlev.social.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.Executor;

@Configuration
public class MailConfiguration {

    @Bean(name = "javaMailSender")
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
