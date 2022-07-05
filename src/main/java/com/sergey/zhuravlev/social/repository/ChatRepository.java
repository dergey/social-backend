package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findBySourceUserAndId(User sourceUser, Long id);

    @Query("select new com.sergey.zhuravlev.social.entity.Chat(c.id, c.sourceUser, c.targetUser, c.linkedChat, c.createAt, c.updateAt, c.messageAllow, c.lastMessage, count(m)) " +
            "from Chat c " +
            "left join Message m on m.chat = c and m.read = false and m.sender = 'TARGET' " +
            "where c.sourceUser = :user " +
            "group by c")
    Page<Chat> findAllBySourceUser(@Param("user") User user, Pageable pageable);

    Optional<Chat> findBySourceUserAndTargetUser(User sourceUser, User targetUser);

}
