package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.MessageType;
import com.sergey.zhuravlev.social.enums.MessageSenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false, updatable = false)
    private MessageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender", length = 20, nullable = false, updatable = false)
    private MessageSenderType sender;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "text")
    private String text;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "is_read", nullable = false)
    private boolean read;

}
