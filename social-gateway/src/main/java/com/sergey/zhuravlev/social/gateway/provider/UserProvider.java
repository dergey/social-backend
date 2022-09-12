package com.sergey.zhuravlev.social.gateway.provider;

import com.sergey.zhuravlev.social.model.user.GetUserByEmailRequest;
import com.sergey.zhuravlev.social.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.*;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserProvider {

    private final ReplyingKafkaTemplate<String, Object, Object> kafkaTemplate;

    public Mono<UserModel> getUserByEmail(String email) {
        GetUserByEmailRequest request = new GetUserByEmailRequest();
        request.setEmail(email);
        return Mono.fromFuture(KafkaUtils.sendAndReceive(
                        kafkaTemplate,
                        "social.user.by-email.get",
                        request,
                        new ParameterizedTypeReference<UserModel>() {})
                .completable())
                .map(message -> (UserModel) message.getPayload());
    }

    public Mono<UserModel> getUserByPhone(Mono<String> phoneMono) {
        return Mono.error(new UnsupportedOperationException());
    }

}
