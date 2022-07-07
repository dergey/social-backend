package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.RegistrationDto;
import com.sergey.zhuravlev.social.dto.UserDto;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.UserMapper;
import com.sergey.zhuravlev.social.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody RegistrationDto registrationDto) {
        User user = registrationService.registerUser(
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                registrationDto.getUsername(),
                registrationDto.getFirstName(),
                registrationDto.getMiddleName(),
                registrationDto.getSecondName(),
                registrationDto.getCity(),
                registrationDto.getBirthDate());
        return userMapper.userToUserDto(user);
    }

}
