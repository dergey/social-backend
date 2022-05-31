package com.sergey.zhuravlev.social.dto.message;

import com.sergey.zhuravlev.social.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTextMessageDto extends CreateMessageDto {

    private String text;

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }
}
