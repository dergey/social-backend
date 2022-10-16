package com.sergey.zhuravlev.social.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncSyncConfiguration {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return new SyncTaskExecutor();
    }
}