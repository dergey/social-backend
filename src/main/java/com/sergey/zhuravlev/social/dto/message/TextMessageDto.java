package com.sergey.zhuravlev.social.dto.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TextMessageDto extends MessageDto {

    private String text;

}
