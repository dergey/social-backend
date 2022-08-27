package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.NewUser;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.Gender;
import com.sergey.zhuravlev.social.enums.RegistrationStatus;
import com.sergey.zhuravlev.social.exception.SocialServiceException;
import com.sergey.zhuravlev.social.exception.SocialServiceFieldException;
import com.sergey.zhuravlev.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    Pattern EMAIL_OR_PHONE_PATTERN = Pattern.compile("(\\d{7,15})|(\\w+@\\w+\\.\\w{2,3})");

    private final NewUserService newUserService;
    private final UserService userService;
    private final ImageService imageService;
    private final ProfileService profileService;

    private final UserRepository userRepository;

    @Transactional
    public NewUser startRegistration(String phoneOrEmail) {
        Matcher matcher = EMAIL_OR_PHONE_PATTERN.matcher(phoneOrEmail);
        if (!matcher.matches()) {
            throw new SocialServiceFieldException("phoneOrEmail", ErrorCode.INVALID_EMAIL_OR_PHONE_FORMAT);
        }
        String phone = matcher.group(1);
        String email = matcher.group(2);
        NewUser newUser;
        if (Strings.isBlank(phone) && Strings.isNotBlank(email)) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new SocialServiceFieldException("phoneOrEmail", ErrorCode.ALREADY_EXIST);
            }
            newUser = newUserService.createNewUserWithEmail(email);
        } else {
            newUser = newUserService.createNewUserWithPhone(phone);
        }
        return newUser;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = {SocialServiceException.class})
    public NewUser confirmByManualCode(UUID continuationCode, String manualCode) {
        NewUser newUser = newUserService.getNewUserByContinuationCode(continuationCode);
        if (!newUser.getRegistrationStatus().equals(RegistrationStatus.EMAIL_CONFIRMATION) &&
                !newUser.getRegistrationStatus().equals(RegistrationStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_NEW_USER_STATE);
        }
        newUserService.confirmByManualCode(newUser, manualCode);
        return newUser;
    }

    @Transactional(noRollbackFor = {SocialServiceException.class})
    public NewUser confirmByLinkCode(String linkCode) {
        NewUser newUser = newUserService.getNewUserByLinkCode(linkCode);
        if (!newUser.getRegistrationStatus().equals(RegistrationStatus.EMAIL_CONFIRMATION) &&
                !newUser.getRegistrationStatus().equals(RegistrationStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_NEW_USER_STATE);
        }
        newUserService.confirmByLinkCode(newUser, linkCode);
        return newUser;
    }

    @Transactional
    public NewUser resendConfirmation(UUID continuationCode) {
        NewUser newUser = newUserService.getNewUserByContinuationCode(continuationCode);
        if (!newUser.getRegistrationStatus().equals(RegistrationStatus.EMAIL_CONFIRMATION) &&
                !newUser.getRegistrationStatus().equals(RegistrationStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_NEW_USER_STATE);
        }
        newUserService.renewConfirmation(newUser);
        return newUser;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User completeRegistration(UUID continuationCode, String rawPassword, String username,
                                     String firstName, String middleName, String secondName,
                                     Gender gender, LocalDate birthDate) {
        NewUser newUser = newUserService.getNewUserByContinuationCode(continuationCode);
        if (!newUser.getRegistrationStatus().equals(RegistrationStatus.PERSONAL_DATA_AWAIT)) {
            throw new SocialServiceException(ErrorCode.INVALID_NEW_USER_STATE);
        }
        User user;
        if (!Objects.isNull(newUser.getEmail())) {
             user = userService.createUserWithEmail(newUser.getEmail(), rawPassword);
        } else if (!Objects.isNull(newUser.getPhone())) {
            user = userService.createUserWithPhone(newUser.getEmail(), rawPassword);
        } else {
            throw new IllegalStateException("Email or phone not provided");
        }
        Image avatarImage = imageService.generateAvatarImage(user, firstName, secondName);
        Profile profile = profileService.createProfile(user, username, avatarImage, firstName, middleName, secondName,
                gender, birthDate);
        user.setProfile(profile);
        return user;
    }

}
