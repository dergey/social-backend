package com.sergey.zhuravlev.social.user.config.properties;

import com.sergey.zhuravlev.socail.kafka.GenerateTopics;
import com.sergey.zhuravlev.socail.kafka.KafkaTopicConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@GenerateTopics
@ConfigurationProperties("social.kafka.topic")
public class KafkaTopicsProperties {

    private KafkaTopicConfiguration getUserByEmail;
    private KafkaTopicConfiguration getUserByPhone;

}
