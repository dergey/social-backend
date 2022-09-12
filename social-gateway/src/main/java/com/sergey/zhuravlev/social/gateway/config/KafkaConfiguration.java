package com.sergey.zhuravlev.social.gateway.config;

import com.sergey.zhuravlev.social.gateway.config.properties.KafkaTopicsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate(ProducerFactory<String, Object> pf,
                                                                               ConcurrentKafkaListenerContainerFactory<String, Object> factory) {
        ConcurrentMessageListenerContainer<String, Object> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, Object> replyTemplate(ProducerFactory<String, Object> pf,
                                                       ConcurrentKafkaListenerContainerFactory<String, Object> factory) {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

}
