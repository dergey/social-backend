package com.sergey.zhuravlev.social.dto.sticker;

import com.sergey.zhuravlev.social.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StickerDto extends ImageDto {

    private String emoji;

}
