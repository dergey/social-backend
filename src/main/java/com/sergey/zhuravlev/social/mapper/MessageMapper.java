package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.message.ImageMessageDto;
import com.sergey.zhuravlev.social.dto.message.MessageDto;
import com.sergey.zhuravlev.social.dto.message.StickerMessageDto;
import com.sergey.zhuravlev.social.dto.message.TextMessageDto;
import com.sergey.zhuravlev.social.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public static MessageDto messageToMessageDto(Message message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto;
        switch (message.getType()) {
            case TEXT:
                TextMessageDto textMessageDto = new TextMessageDto();
                textMessageDto.setText(message.getText());
                messageDto = textMessageDto;
                break;
            case IMAGE:
                messageDto = new ImageMessageDto();
                break;
            case STICKER:
                messageDto = new StickerMessageDto();
                break;
            default:
                throw new IllegalArgumentException(String.format("%s message.type not supported", message.getType()));
        }
        messageDto.setId(message.getId());
        messageDto.setType(message.getType());
        messageDto.setSender(message.getSender());
        messageDto.setCreateAt(message.getCreateAt());
        messageDto.setUpdateAt(message.getUpdateAt());
        messageDto.setRead(message.isRead());
        return messageDto;
    }

}
