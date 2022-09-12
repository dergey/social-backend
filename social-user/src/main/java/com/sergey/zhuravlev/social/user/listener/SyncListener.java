package com.sergey.zhuravlev.social.user.listener;

import com.sergey.zhuravlev.social.model.user.GetUserByEmailRequest;
import com.sergey.zhuravlev.social.model.user.GetUserByPhoneRequest;
import com.sergey.zhuravlev.social.model.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SyncListener {

    @KafkaListener(topics = "${social.kafka.topic.get-user-by-email.name}",
            concurrency = "${social.kafka.topic.get-user-by-email.concurrency}",
            groupId = "${social.kafka.topic.get-user-by-email.group-id}")
    public UserModel getUserByEmail(@Payload GetUserByEmailRequest request) {
        log.info("Incoming request {}", request);
        return null;
    }

    @KafkaListener(topics = "${social.kafka.topic.get-user-by-phone.name}",
            concurrency = "${social.kafka.topic.get-user-by-phone.concurrency}",
            groupId = "${social.kafka.topic.get-user-by-phone.group-id}")
    public UserModel getUserByPhone(@Payload GetUserByPhoneRequest request) {
        log.info("Incoming request {}", request);
        return null;
    }

}
