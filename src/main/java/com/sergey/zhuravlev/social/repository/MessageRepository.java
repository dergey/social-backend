package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Message;
import com.sergey.zhuravlev.social.enums.MessageSenderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByChat(Chat chat, Pageable pageable);

    List<Message> findAllByChatOrderByCreateAtAsc(Chat chat, Pageable pageable);

    Optional<Message> findFirstByChatOrderByCreateAtAsc(Chat chat);

    Optional<Message> findByIdAndChat(Long messageId, Chat chat);

    void deleteByIdAndChat(Long messageId, Chat chat);

    @Modifying
    @Query("update Message m set m.read = true where m.chat = ?1 and m.sender = ?2")
    void updateMessageReadByChat(Chat chat, MessageSenderType sender);

}
