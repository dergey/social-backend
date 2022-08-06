package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.UserDto;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.UserMapper;
import com.sergey.zhuravlev.social.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User endpoints")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public UserDto getCurrentUser() {
        User user = userService.getCurrentUser();
        return userMapper.userToUserDto(user);
    }

}
