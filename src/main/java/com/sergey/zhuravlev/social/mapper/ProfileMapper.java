package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.ProfileDetailDto;
import com.sergey.zhuravlev.social.dto.ProfileDto;
import com.sergey.zhuravlev.social.entity.Profile;
import org.mapstruct.Mapper;
import org.springframework.util.unit.DataSize;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto profileToProfileDto(Profile profile);
    ProfileDetailDto profileToProfileDetailDto(Profile profile);

    default String map(DataSize value) {
        return value.toKilobytes() + "KB";
    }
}
