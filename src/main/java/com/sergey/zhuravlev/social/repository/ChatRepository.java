package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findBySourceUserAndId(User sourceUser, Long id);

    Optional<Chat> findBySourceUserAndTargetUser(User sourceUser, User targetUser);

    Page<Chat> findAllBySourceUser(User user, Pageable pageable);

}
