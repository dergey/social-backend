package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.dto.event.MessageCreatedEventDto;
import com.sergey.zhuravlev.social.dto.event.MessageDeletedEventDto;
import com.sergey.zhuravlev.social.dto.event.MessageUpdatedEventDto;
import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Message;
import com.sergey.zhuravlev.social.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageWebsocketService {

    private static final String DEFAULT_MESSAGE_DESTINATION = "/topic/messages";

    private final SimpMessageSendingOperations messagingTemplate;

    @Async
    public void notifyMessageCreated(Message message) {
        MessageCreatedEventDto payload = new MessageCreatedEventDto();
        payload.setChatId(message.getChat().getId());
        payload.setMessage(MessageMapper.messageToMessageDto(message));
        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getChat().getSourceUser().getEmail()),
                DEFAULT_MESSAGE_DESTINATION,
                payload);
        log.info("WS message send: {}", payload);
    }

    @Async
    public void notifyMessageUpdated(Message message) {
        MessageUpdatedEventDto payload = new MessageUpdatedEventDto();
        payload.setChatId(message.getChat().getId());
        payload.setMessage(MessageMapper.messageToMessageDto(message));
        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getChat().getSourceUser().getEmail()),
                DEFAULT_MESSAGE_DESTINATION,
                payload);
        log.info("WS message send: {}", payload);
    }

    @Async
    public void notifyMessageDeleted(Chat chat, Long messageId) {
        MessageDeletedEventDto payload = new MessageDeletedEventDto();
        payload.setChatId(chat.getId());
        payload.setMessageId(messageId);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chat.getSourceUser().getEmail()),
                DEFAULT_MESSAGE_DESTINATION,
                payload);
        log.info("WS message send: {}", payload);
    }

}
