package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.dto.message.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatPreviewDto {

    private Long id;

    private ProfilePreviewDto targetProfile;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private boolean messageAllow;

    private Long unreadMessages;

    private MessageDto lastMessage;

}
