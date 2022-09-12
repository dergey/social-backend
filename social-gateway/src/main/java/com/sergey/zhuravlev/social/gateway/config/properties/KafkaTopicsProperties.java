package com.sergey.zhuravlev.social.gateway.config.properties;

import com.sergey.zhuravlev.socail.kafka.KafkaTopicConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("social.kafka.topic")
public class KafkaTopicsProperties {

    private KafkaTopicConfiguration getUserByEmail;
    private KafkaTopicConfiguration getUserByPhone;

}
