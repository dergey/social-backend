package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.ImageDto;
import com.sergey.zhuravlev.social.entity.Image;
import org.mapstruct.Mapper;
import org.springframework.util.unit.DataSize;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto imageToImageDto(Image image);

    default String map(DataSize value) {
        return value.toKilobytes() + "KB";
    }
}
