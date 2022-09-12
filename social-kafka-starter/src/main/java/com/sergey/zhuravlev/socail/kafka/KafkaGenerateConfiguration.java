package com.sergey.zhuravlev.socail.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaGenerateConfiguration {

    @Bean
    public KafkaGenerateTopicBeanPostProcessor kafkaGenerateTopicBeanPostProcessor() {
        return new KafkaGenerateTopicBeanPostProcessor();
    }

}
