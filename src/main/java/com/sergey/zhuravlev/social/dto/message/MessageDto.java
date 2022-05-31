package com.sergey.zhuravlev.social.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergey.zhuravlev.social.enums.MessageSenderType;
import com.sergey.zhuravlev.social.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MessageDto {

    private Long id;

    private MessageType type;

    private MessageSenderType sender;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @JsonProperty("isRead")
    private boolean read;

}
