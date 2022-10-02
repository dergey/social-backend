package com.sergey.zhuravlev.social.configuration;

import com.sergey.zhuravlev.social.configuration.properties.CorsProperties;
import com.sergey.zhuravlev.social.configuration.properties.MailContextProperties;
import com.sergey.zhuravlev.social.configuration.properties.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableConfigurationProperties({CorsProperties.class, StorageProperties.class, MailContextProperties.class})
public class SocialAppConfiguration {
}
