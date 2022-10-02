package com.sergey.zhuravlev.social.dto.event;

import com.sergey.zhuravlev.social.enums.MessageAction;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDeletedEventDto {

    private MessageAction action = MessageAction.DELETED;

    private Long chatId;

    private Long messageId;

}
