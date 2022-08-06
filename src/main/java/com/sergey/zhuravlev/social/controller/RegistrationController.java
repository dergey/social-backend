package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.UserDto;
import com.sergey.zhuravlev.social.dto.registration.*;
import com.sergey.zhuravlev.social.entity.NewUser;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.UserMapper;
import com.sergey.zhuravlev.social.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "Registration endpoints")
@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final UserMapper userMapper;

    @Operation(description = "Initializes the registration flow")
    @PostMapping
    public RegistrationStatusDto startRegistration(@Valid @RequestBody StartRegistrationDto dto) {
        NewUser newUser = registrationService.startRegistration(dto.getPhoneOrEmail());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @Operation(description = "Confirms email or phone by code")
    @PostMapping("/confirm/code")
    public RegistrationStatusDto confirmByCode(@Valid @RequestBody ManualCodeConfirmationDto dto) {
        NewUser newUser = registrationService.confirmByManualCode(dto.getContinuationCode(), dto.getManualCode());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @Operation(description = "Confirms email by link (long code)")
    @PostMapping("/confirm/link")
    public RegistrationStatusDto confirmByLink(@Valid @NotBlank @RequestParam @Schema(example = "R0dj6wT9Uu8fuYXk") String linkCode) {
        NewUser newUser = registrationService.confirmByLinkCode(linkCode);
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @Operation(description = "Re-sends confirmation message")
    @PostMapping("/resend")
    public RegistrationStatusDto resendConfirmation(@Valid @RequestBody ContinuationDto continuationDto) {
        NewUser newUser = registrationService.resendConfirmation(continuationDto.getContinuationCode());
        return new RegistrationStatusDto(newUser.getRegistrationStatus(), newUser.getContinuationCode());
    }

    @Operation(description = "Completes the registration flow, sets the data for the profile")
    @PostMapping("/complete")
    public UserDto completeRegistration(@Valid @RequestBody CompleteRegistrationDto dto) {
        User user = registrationService.completeRegistration(dto.getContinuationCode(), dto.getPassword(),
                dto.getUsername(), dto.getFirstName(), dto.getMiddleName(), dto.getSecondName(), dto.getCity(),
                dto.getBirthDate());
        return userMapper.userToUserDto(user);
    }

}
