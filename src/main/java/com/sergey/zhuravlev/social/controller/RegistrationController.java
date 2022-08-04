package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.UserDto;
import com.sergey.zhuravlev.social.dto.registration.*;
import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.NewUser;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.UserMapper;
import com.sergey.zhuravlev.social.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final UserMapper userMapper;

    @PostMapping
    public RegistrationStatusDto startRegistration(@Valid @RequestBody StartRegistrationDto dto) {
        NewUser newUser = registrationService.startRegistration(dto.getPhoneOrEmail());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @PostMapping("/confirm/code")
    public RegistrationStatusDto confirmByCode(@Valid @RequestBody ManualCodeConfirmationDto dto) {
        NewUser newUser = registrationService.confirmByManualCode(dto.getContinuationCode(), dto.getManualCode());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @PostMapping("/confirm/link")
    public RegistrationStatusDto confirmByLink(@Valid @NotBlank @RequestParam String linkCode) {
        NewUser newUser = registrationService.confirmByLinkCode(linkCode);
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @PostMapping("/resend")
    public RegistrationStatusDto resendConfirmation(@Valid @RequestBody ContinuationDto continuationDto) {
        NewUser newUser = registrationService.resendConfirmation(continuationDto.getContinuationCode());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }


    @PostMapping("/complete")
    public UserDto completeRegistration(@Valid @RequestBody CompleteRegistrationDto dto) {
        User user = registrationService.completeRegistration(dto.getContinuationCode(), dto.getPassword(),
                dto.getUsername(), dto.getFirstName(), dto.getMiddleName(), dto.getSecondName(), dto.getCity(),
                dto.getBirthDate());
        return userMapper.userToUserDto(user);
    }

}
