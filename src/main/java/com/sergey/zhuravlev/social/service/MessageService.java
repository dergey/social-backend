package com.sergey.zhuravlev.social.service;

import com.google.common.collect.Iterables;
import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Message;
import com.sergey.zhuravlev.social.enums.MessageSenderType;
import com.sergey.zhuravlev.social.enums.MessageType;
import com.sergey.zhuravlev.social.repository.ChatRepository;
import com.sergey.zhuravlev.social.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public Message getMessage(Chat chat, Long messageId) {
        return messageRepository.findByIdAndChat(messageId, chat)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Message not found by id %s for chat %s", messageId, chat.getId())));
    }

    @Transactional(readOnly = true)
    public Page<Message> getChatMessages(Chat chat, Pageable pageable) {
        return messageRepository.findAllByChat(chat, pageable);
    }

    @Transactional(readOnly = true)
    public Collection<Message> getChatLastMessages(Chat chat, Integer size) {
        return messageRepository.findAllByChatOrderByCreateAtAsc(chat, PageRequest.of(0, size));
    }

    @Transactional
    public Message createTextMessage(Chat chat, String text) {
        chat = chatRepository.getById(chat.getId());
        Message linkedMessage = messageRepository.save(new Message(null,
                chat.getLinkedChat(),
                MessageType.TEXT,
                MessageSenderType.TARGET,
                null,
                text,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false));
        chat.getLinkedChat().setLastMessage(linkedMessage);
        Message message = messageRepository.save(new Message(null,
                chat,
                MessageType.TEXT,
                MessageSenderType.SOURCE,
                null,
                text,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false)); // read by other user!
        chat.setLastMessage(message);
        return message;
    }


    @Transactional
    public Message createImageMessage(Chat chat, Image image) {
        chat = chatRepository.getById(chat.getId());
        Message linkedMessage = messageRepository.save(new Message(null,
                chat.getLinkedChat(),
                MessageType.IMAGE,
                MessageSenderType.TARGET,
                image,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false));
        chat.getLinkedChat().setLastMessage(linkedMessage);
        Message message = messageRepository.save(new Message(null,
                chat,
                MessageType.IMAGE,
                MessageSenderType.SOURCE,
                image,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false));
        chat.setLastMessage(message);
        return message;
    }

    @Transactional
    public Message updateTextMessage(Message message, String text) {
        message = messageRepository.getById(message.getId());
        message.setText(text);
        return message;
    }

    @Transactional
    public void deleteMessage(Chat chat, Long messageId) {
        chat = chatRepository.getById(chat.getId());
        if (!messageRepository.findByIdAndChat(messageId, chat).isPresent()) {
            throw new EntityNotFoundException(String.format("Message not found by id %s for chat %s", messageId, chat.getId()));
        }
        if (chat.getLastMessage() != null && chat.getLastMessage().getId().equals(messageId)) {
            List<Message> lastMessages = messageRepository.findAllByChatOrderByCreateAtAsc(chat, PageRequest.of(0, 2));
            if (lastMessages.size() > 1) {
                chat.setLastMessage(Iterables.getLast(lastMessages));
            } else {
                chat.setLastMessage(null);
            }
        }
        messageRepository.deleteByIdAndChat(messageId, chat);
    }
}
