package com.sergey.zhuravlev.social.dto.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sergey.zhuravlev.social.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateTextMessageDto.class, name = "TEXT"),
        @JsonSubTypes.Type(value = CreateStickerMessageDto.class, name = "STICKER")
})
public abstract class CreateMessageDto {

    private MessageType type;

}
