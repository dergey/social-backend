package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.MessageSenderType;
import com.sergey.zhuravlev.social.repository.ChatRepository;
import com.sergey.zhuravlev.social.repository.MessageRepository;
import com.sergey.zhuravlev.social.repository.ProfileRepository;
import com.sergey.zhuravlev.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public Chat getChat(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Chat not found by id %s", id)));
    }

    @Transactional(readOnly = true)
    public Chat getChat(User user, Long id) {
        return chatRepository.findBySourceUserAndId(user, id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Chat not found by id %s for user %s", id, user.getEmail())));
    }

    @Transactional(readOnly = true)
    public Page<Chat> getChats(User user, Pageable pageable) {
        return chatRepository.findAllBySourceUser(user, pageable);
    }

    @Transactional
    public Chat getOrCreateChat(User user, Profile targetProfile) {
        user = userRepository.getById(user.getId());
        targetProfile = profileRepository.getById(targetProfile.getId());

        Chat chat = chatRepository.findBySourceUserAndTargetUser(user, targetProfile.getUser()).orElse(null);
        if (chat != null) {
            return chat;
        }

        chat = chatRepository.save(new Chat(null,
                user,
                targetProfile.getUser(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                null,
                null));

        Chat inverseChat = chatRepository.save(new Chat(null,
                targetProfile.getUser(),
                user,
                chat,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                null,
                null));

        chat.setLinkedChat(inverseChat);

        return chat;
    }

    @Transactional
    public Chat updateReadStatus(Chat chat) {
        chat = chatRepository.getById(chat.getId());
        messageRepository.updateMessageReadByChat(chat, MessageSenderType.TARGET);
        messageRepository.updateMessageReadByChat(chat.getLinkedChat(), MessageSenderType.SOURCE);
        return chat;
    }

}
