package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.dto.message.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private Long id;

    private ProfileDto targetProfile;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private boolean messageAllow;

    private Collection<MessageDto> lastMessages;

}
