package com.sergey.zhuravlev.social.gateway.config;

import com.sergey.zhuravlev.social.gateway.config.properties.KafkaTopicsProperties;
import com.sergey.zhuravlev.social.gateway.config.properties.SocialWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableConfigurationProperties({SocialWebProperties.class, KafkaTopicsProperties.class})
public class SocialGatewayConfiguration {
}
