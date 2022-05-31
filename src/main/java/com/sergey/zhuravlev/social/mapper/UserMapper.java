package com.sergey.zhuravlev.social.mapper;

import com.sergey.zhuravlev.social.dto.UserDto;
import com.sergey.zhuravlev.social.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.unit.DataSize;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = "user.profile.username")
    @Mapping(target = "avatar", source = "user.profile.avatar")
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "firstName", source = "user.profile.firstName")
    @Mapping(target = "middleName", source = "user.profile.middleName")
    @Mapping(target = "secondName", source = "user.profile.secondName")
    @Mapping(target = "birthDate", source = "user.profile.birthDate")
    UserDto userToUserDto(User user);

    default String map(DataSize value) {
        return value.toKilobytes() + "KB";
    }
}
