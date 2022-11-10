package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.ProfileDto;
import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
import com.sergey.zhuravlev.social.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.unit.DataSize;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto profileToProfileDto(Profile profile);

    @Mapping(target = "city", source = "residenceAddress.city")
    ProfilePreviewDto profileToProfilePreviewDto(Profile profile);

    default String map(DataSize value) {
        return value.toKilobytes() + "KB";
    }
}
