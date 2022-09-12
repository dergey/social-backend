package org.springframework.kafka.requestreply;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.messaging.support.GenericMessage;

public class KafkaUtils {

    public static <R> RequestReplyTypedMessageFuture<String, Object, R> sendAndReceive(
            ReplyingKafkaTemplate<String, Object, Object> kafkaTemplate,
            String topic,
            Object request,
            @Nullable ParameterizedTypeReference<R> returnType) {
        RequestReplyFuture<String, Object, Object> future = kafkaTemplate.sendAndReceive(
                (ProducerRecord<String, Object>) kafkaTemplate.getMessageConverter()
                        .fromMessage(new GenericMessage<>(request), topic));
        RequestReplyTypedMessageFuture<String, Object, R> replyFuture =
                new RequestReplyTypedMessageFuture<>(future.getSendFuture());
        future.addCallback(
                result -> {
                    try {
                        replyFuture.set(kafkaTemplate.getMessageConverter()
                                .toMessage(result, null, null, returnType == null ? null : returnType.getType()));
                    } catch (Exception ex) {
                        replyFuture.setException(ex);
                    }
                },
                replyFuture::setException);
        return replyFuture;
    }

}
