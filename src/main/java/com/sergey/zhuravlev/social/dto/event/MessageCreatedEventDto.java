package com.sergey.zhuravlev.social.dto.event;

import com.sergey.zhuravlev.social.dto.message.MessageDto;
import com.sergey.zhuravlev.social.enums.MessageAction;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreatedEventDto {

    private MessageAction action = MessageAction.CREATED;

    private Long chatId;

    private MessageDto message;

}
