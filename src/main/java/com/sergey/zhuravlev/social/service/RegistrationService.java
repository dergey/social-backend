package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ImageService imageService;
    private final ProfileService profileService;

    @Transactional
    public User registerUser(String email, String rawPassword, String username, String firstName, String middleName, String secondName, LocalDate birthDate) {
        User user = userService.createUser(email, rawPassword);
        Image avatarImage = imageService.generateAvatarImage(user, firstName, secondName);
        Profile profile = profileService.createProfile(user, username, avatarImage, firstName, middleName, secondName, birthDate);
        user.setProfile(profile);
        return user;
    }

}
