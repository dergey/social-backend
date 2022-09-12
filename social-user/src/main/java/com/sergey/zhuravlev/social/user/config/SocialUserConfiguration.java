package com.sergey.zhuravlev.social.user.config;

import com.sergey.zhuravlev.social.user.config.properties.KafkaTopicsProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableConfigurationProperties({KafkaTopicsProperties.class, LiquibaseProperties.class, R2dbcProperties.class})
public class SocialUserConfiguration {
}
