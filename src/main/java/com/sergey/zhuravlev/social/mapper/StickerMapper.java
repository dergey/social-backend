package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.sticker.StickerDto;
import com.sergey.zhuravlev.social.dto.sticker.StickerPackDto;
import com.sergey.zhuravlev.social.entity.Sticker;
import com.sergey.zhuravlev.social.entity.StickerPack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {

    StickerDto stickerToStickerDto(Sticker sticker);
    StickerPackDto stickerPackToStickerPackDto(StickerPack stickerPack);

}
