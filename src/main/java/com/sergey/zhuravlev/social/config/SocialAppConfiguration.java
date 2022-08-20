package com.sergey.zhuravlev.social.config;

import com.sergey.zhuravlev.social.config.properties.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class SocialAppConfiguration {
}
