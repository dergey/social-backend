package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.*;
import com.sergey.zhuravlev.social.dto.message.MessageDto;
import com.sergey.zhuravlev.social.entity.Chat;
import com.sergey.zhuravlev.social.entity.Message;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "targetProfile", source = "chat.targetUser.profile")
    @Mapping(target = "lastMessages", source = "lastMessages", qualifiedByName = "messageToMessageDto")
    ChatDto chatAndLastMessagesToChatDto(Chat chat, Collection<Message> lastMessages);

    @Mapping(target = "targetProfile", source = "chat.targetUser.profile")
    @Mapping(target = "lastMessage", source = "chat.lastMessage", qualifiedByName = "messageToMessageDto")
    ChatPreviewDto chatToChatPreviewDto(Chat chat);

    @Named("messageToMessageDto")
    default MessageDto messageToMessageDto(Message message) {
        return MessageMapper.messageToMessageDto(message);
    }

}
